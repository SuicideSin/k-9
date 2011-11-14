package info.guardianproject.doghouse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import net.sourceforge.jsocks.socks.Proxy;
import net.sourceforge.jsocks.socks.Socks5Proxy;
import net.sourceforge.jsocks.socks.SocksException;
import net.sourceforge.jsocks.socks.SocksSocket;

public class ProxyManager {


    public static final int PROXY_TYPE_SOCKS4 = 1;
    public static final int PROXY_TYPE_SOCKS5 = 2;

	private Socks5Proxy mProxy;
	
	public ProxyManager (String host, int port) throws UnknownHostException
	{
		Proxy.setDefaultProxy(host, port);
		mProxy = new Socks5Proxy(host, port);
		mProxy.resolveAddrLocally(false);
	}
	
	public Socket connectSocketToProxy (Socket socket, InetSocketAddress sAddress) throws IOException
	{
		 SocksSocket proxySocket = new SocksSocket(mProxy, sAddress.getHostName(), sAddress.getPort());
		 
		 socket.bind(proxySocket.getLocalSocketAddress());
		 
		 return proxySocket;
	}
	
	public Socket getProxiedSocket (InetSocketAddress sAddress) throws SocksException, UnknownHostException
	{
		 return new SocksSocket(mProxy, sAddress.getHostName(), sAddress.getPort());

	}
}
