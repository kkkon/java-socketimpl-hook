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
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketOptions;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kiyofumi Kondoh
 */
public class SocketImplHook extends SocketImpl implements SocketOptions
{

    private final SocketImpl socketImpl;

    private Method _create;
    private Method _connectHostPort;
    private Method _connectInetAddrPort;
    private Method _connectSocketAddrPort;
    private Method _bind;
    private Method _listen;
    private Method _accept;
    private Method _getInputStream;
    private Method _getOutputStream;
    private Method _available;
    private Method _close;
    private Method _sendUrgantData;

    public SocketImplHook(SocketImpl socketImpl) {

        boolean reflected = true;

        if ( null != socketImpl )
        {
            try
            {
                final Class<?>  clazz = this.socketImpl.getClass();

                _create = clazz.getDeclaredMethod( "create", new Class<?>[]{ boolean.class } );
                _connectHostPort = clazz.getDeclaredMethod( "connect", new Class<?>[]{ String.class, int.class } );
                _connectInetAddrPort = clazz.getDeclaredMethod( "connect", new Class<?>[]{ InetAddress.class, int.class } );
                _connectSocketAddrPort = clazz.getDeclaredMethod( "connect", new Class<?>[]{ SocketAddress.class, int.class } );
                _bind = clazz.getDeclaredMethod( "bind", new Class<?>[]{ InetAddress.class, int.class } );
                _listen = clazz.getDeclaredMethod( "listen", new Class<?>[]{ int.class } );
                _accept = clazz.getDeclaredMethod( "accept", new Class<?>[]{ SocketImpl.class } );
                _getInputStream = clazz.getDeclaredMethod( "getInputStream", new Class<?>[]{  } );
                _getOutputStream = clazz.getDeclaredMethod( "getOutputStream", new Class<?>[]{  } );
                _available = clazz.getDeclaredMethod( "available", new Class<?>[]{ } );
                _close = clazz.getDeclaredMethod( "close", new Class<?>[]{ } );
                _sendUrgantData = clazz.getDeclaredMethod( "sendUrgantData", new Class<?>[]{ int.class } );
            }
            catch ( Exception e )
            {
                reflected = false;
            }
        }

        if ( reflected )
        {
            this.socketImpl = socketImpl;
        }
        else
        {
            this.socketImpl = null;
        }
    }

    @Override
    protected void create(boolean stream) throws IOException {
        if ( null != socketImpl && null != this._create )
        {
            try
            {
                this._create.invoke( this.socketImpl, new Object[] { stream } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void connect(String host, int port) throws IOException {
        Logger.getLogger(SocketImplHook.class.getName()).log(Level.INFO, "host=" + host + ",port=" + port );

        if ( null != this.socketImpl && null != this._connectHostPort )
        {
            try
            {
                this._connectHostPort.invoke( this.socketImpl, new Object[] { host, port } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void connect(InetAddress address, int port) throws IOException {
        Logger.getLogger(SocketImplHook.class.getName()).log(Level.INFO, "InetAddr=" + address.toString() + ",port=" + port );

        if ( null != this.socketImpl && null != this._connectInetAddrPort )
        {
            try
            {
                this._connectInetAddrPort.invoke( this.socketImpl, new Object[] { address, port } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void connect(SocketAddress address, int timeout) throws IOException {
        Logger.getLogger(SocketImplHook.class.getName()).log(Level.INFO, "SocketAddr=" + address.toString() + ",port=" + port );

        if ( null != this.socketImpl && null != this._connectSocketAddrPort )
        {
            try
            {
                this._connectSocketAddrPort.invoke( this.socketImpl, new Object[] { address, timeout } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void bind(InetAddress host, int port) throws IOException {
        if ( null != this.socketImpl && null != this._bind )
        {
            try
            {
                this._bind.invoke( this.socketImpl, new Object[] { host, port } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void listen(int backlog) throws IOException {
        if ( null != this.socketImpl && null != this._listen )
        {
            try
            {
                this._listen.invoke( this.socketImpl, new Object[] { backlog } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void accept(SocketImpl s) throws IOException {
        if ( null != this.socketImpl && null != this._accept )
        {
            try
            {
                this._accept.invoke( this.socketImpl, new Object[] { s } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        InputStream inStream = null;

        if ( null != this.socketImpl && null != this._getInputStream )
        {
            try
            {
                inStream = (InputStream)this._getInputStream.invoke( this.socketImpl, new Object[] {} );
            }
            catch ( ClassCastException ex )
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }

        return inStream;
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        OutputStream outStream = null;

        if ( null != this.socketImpl && null != this._getInputStream )
        {
            try
            {
                outStream = (OutputStream)this._getOutputStream.invoke( this.socketImpl, new Object[] {} );
            }
            catch ( ClassCastException ex )
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }

        return outStream;
    }

    @Override
    protected int available() throws IOException {
        int result = -1;

        if ( null != this.socketImpl && null != this._available )
        {
            try
            {
                result = (Integer)this._available.invoke( this.socketImpl, new Object[] {} );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }

        return result;
    }

    @Override
    protected void close() throws IOException {
        if ( null != this.socketImpl && null != this._close )
        {
            try
            {
                this._close.invoke( this.socketImpl, new Object[] {} );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void sendUrgentData(int data) throws IOException {
        if ( null != this.socketImpl && null != this._sendUrgantData )
        {
            try
            {
                this._sendUrgantData.invoke( this.socketImpl, new Object[] { data } );
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(SocketImplHook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //throw new RuntimeException();
        }
    }

    public void setOption(int optID, Object value) throws SocketException {
        if ( null != this.socketImpl )
        {
            this.socketImpl.setOption( optID, value );
        }
    }

    public Object getOption(int optID) throws SocketException {
        Object result = null;

        if ( null != this.socketImpl )
        {
            result = this.socketImpl.getOption( optID );
        }

        return result;
    }

}
