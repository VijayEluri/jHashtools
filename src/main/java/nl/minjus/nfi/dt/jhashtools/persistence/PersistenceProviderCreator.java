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

package nl.minjus.nfi.dt.jhashtools.persistence;

/**
 * Utility class for construction of PersistenceProviders.
 *
 * @author Erwin van Eijk
 */
public class PersistenceProviderCreator
{
    /**
     * Create a new PersistenceProvider for <c>thePersistenceStyle<c>.
     *
     * @param thePersistenceStyle
     *            the style to create persistency for.
     * @return a provider.
     */
    public static PersistenceProvider create(final PersistenceStyle thePersistenceStyle) {
        PersistenceProvider persistenceProvider = null;
        switch (thePersistenceStyle) {
            case JSON:
                persistenceProvider = new JsonPersistenceProvider(true);
                break;
            case OLDSTYLE:
                persistenceProvider = new OldStylePersistenceProvider();
                break;
            case XML:
                throw new RuntimeException("XML is not yet supported.");
            default:
                throw new UnsupportedOperationException("We know nothing of persistenceStyle "
                    + thePersistenceStyle);
        }
        return persistenceProvider;
    }
}
