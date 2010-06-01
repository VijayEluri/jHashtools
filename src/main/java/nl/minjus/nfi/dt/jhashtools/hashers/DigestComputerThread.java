/*
 * Copyright (c) 2010 Erwin van Eijk <erwin.vaneijk@gmail.com>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of <copyright holder>.
 */

package nl.minjus.nfi.dt.jhashtools.hashers;

import nl.minjus.nfi.dt.jhashtools.Digest;
import nl.minjus.nfi.dt.jhashtools.DigestResult;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * This callable can be use to create a thread that is able to compute digests in
 * a separate thread.
 *
 * @author Erwin van Eijk
 */
class DigestComputerThread implements Callable<DigestResult>
{
    private static final Logger LOG = getLogger(DigestComputerThread.class.getName());
    private Collection<MessageDigest> digests;
    private Exchanger<ByteBuffer> exchanger;

    /**
     * Constructor.
     *
     * @param digests the digests to use.
     * @param exchanger the exchanger to get the data from.
     */
    public DigestComputerThread(final Collection<MessageDigest> digests, Exchanger<ByteBuffer> exchanger)
    {
        this.digests = digests;
        this.exchanger = exchanger;
    }

    /**
     * The entry point for the thread.
     *
     * @see Callable#call
     *
     * @return a DigestResult instance.
     */
    public DigestResult call()
    {
        try {
            byte[] buf = new byte[AbstractFileHasher.BLOCK_READ_SIZE];
            ByteBuffer buffer = ByteBuffer.wrap(buf, 0, AbstractFileHasher.BLOCK_READ_SIZE);
            while (true) {
                buffer = this.exchanger.exchange(buffer);
                if (buffer == null) {
                    break;
                }
                for (MessageDigest digest : this.digests) {
                    digest.update(buffer);
                }
            }
            final DigestResult res = new DigestResult();
            for (MessageDigest digest : this.digests) {
                res.add(new Digest(digest));
            }
            return res;
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, "Execution was aborted.", ex);
            return null;
        }
    }
}
