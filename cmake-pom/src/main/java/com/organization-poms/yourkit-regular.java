/**
 * Copyright (c) 2014, acme-systems.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the acme-systems.com 
 * nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.organization-poms;

/**
 * Entrance class.
 * @author Lone Wolf (lone-wolf@acme-systems.com)
 * @version $Id$
 * @since 0.2
 */
    // profiling with yourkit, activate using -P youtkit-profile
    private static Object profiler;
 
    private static void startProfiler()
    {
        Class controllerClazz;
        try
        {
            controllerClazz = Class.forName( "com.yourkit.api.Controller" );
        }
        catch ( Exception e )
        {
            log.info( "Profiler not present" );
            return;
        }
 
        try
        {
            profiler = controllerClazz.newInstance();
            controllerClazz.getMethod( "captureMemorySnapshot" ).invoke( profiler );
        }
        catch ( Exception e )
        {
            fail( "Profiler was active, but failed due: " + e.getMessage() );
        }
    }
 
    private static void takeSnapshot()
    {
        if ( profiler != null )
        {
            try
            {
                profiler.getClass().getMethod( "forceGC" ).invoke( profiler );
                profiler.getClass().getMethod( "captureMemorySnapshot" ).invoke( profiler );
            }
            catch ( Exception e )
            {
                fail( "Profiler was active, but failed due: " + e.getMessage() );
            }
        }
    }
