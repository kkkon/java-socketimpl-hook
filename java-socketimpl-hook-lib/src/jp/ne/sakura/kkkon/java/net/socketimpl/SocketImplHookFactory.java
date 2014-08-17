/*
 * The MIT License
 *
 * Copyright 2014 Kiyofumi Kondoh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.ne.sakura.kkkon.java.net.socketimpl;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;

/**
 *
 * @author Kiyofumi Kondoh
 */
public class SocketImplHookFactory implements SocketImplFactory
{
    private static Class<?>   clazz;

    public static synchronized void initialize()
    {
        if ( null != clazz )
        {
            return;
        }

        {
            Socket  socket = new Socket();
            java.lang.reflect.Field[] fields = Socket.class.getDeclaredFields();
            int counter = 0;
            java.lang.reflect.Field implField = null;
            for ( java.lang.reflect.Field field : fields )
            {
                //Log.d( TAG, field.getName() + ":" + field.getType().toString() );
                if ( java.net.SocketImpl.class.equals(field.getType() ) )
                {
                    counter += 1;
                    implField = field;
                }
            }
            if ( 1 == counter && null != implField )
            {
                java.net.SocketImpl socketImpl = null;

                implField.setAccessible( true );
                try
                {
                    socketImpl = (java.net.SocketImpl) implField.get( socket );
                }
                catch ( Exception e )
                {
                    //Log.d( TAG, "got Exception", e );
                }

                if ( null != socketImpl )
                {
                    final Class<?> clazz = socketImpl.getClass();
                    final String className = socketImpl.getClass().getName();
                    //Log.d( TAG, " SocketImpl className=" + className );
                    java.net.SocketImpl newInstance = null;
                    try
                    {
                        newInstance = (java.net.SocketImpl)clazz.newInstance();
                        //Log.i( TAG, " newInstance=" + newInstance );
                        SocketImplHookFactory.clazz = clazz;
                    }
                    catch ( Exception e )
                    {
                        //Log.d( TAG, "got Exception", e );
                    }
                }
            }

            try
            {
                socket.close();
            }
            catch ( IOException e )
            {
                
            }
        }
    }

    public SocketImpl createSocketImpl() {
        final SocketImpl socketImpl;
        if ( null != clazz )
        {
            try
            {
                socketImpl = (java.net.SocketImpl) clazz.newInstance();
                return new SocketImplHook( socketImpl );
            }
            catch ( Exception e )
            {
                
            }
        }

        return null;
    }
}
