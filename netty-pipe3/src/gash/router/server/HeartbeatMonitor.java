package gash.router.server;

import com.sun.corba.se.spi.activation.Server;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sam on 3/25/17.
 */
public class HeartbeatMonitor {

    protected static Logger logger = LoggerFactory.getLogger("cmd");
    protected ChannelFuture channel;
    private HeartbeatHandler heartbeatHandler;
    private EventLoopGroup eventLoopGroup;
    private String host;
    private int port;
    private ServerState state;

    public HeartbeatMonitor(String host,int port,ServerState state){
        this.host=host;
        this.port=port;
        this.state=state;
        this.eventLoopGroup=new NioEventLoopGroup();
    }
    public Channel initChannel(){
        if(channel==null){
            try{
//                WorkHandler handler=new WorkHandler(state);
                WorkInit mi=new WorkInit(state, false);
                Bootstrap b=new Bootstrap();
                b.group(eventLoopGroup).channel(NioSocketChannel.class).handler(mi);
                b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
                b.option(ChannelOption.TCP_NODELAY, true);
                b.option(ChannelOption.SO_KEEPALIVE, true);
                logger.info("Monitor"+this.host+","+this.port);
                channel = b.connect(host, port).syncUninterruptibly();
//                channel.awaitUninterruptibly(5000l);
//                channel.channel().closeFuture().addListener(new MonitorClosedListener(this));
            }catch(Exception e){
                logger.info("channel create failed!");
            }
        }
        if(channel!=null)
            return channel.channel();
        else
            throw new RuntimeException("can not establish channel to server");
    }

    public static class MonitorClosedListener implements ChannelFutureListener {
        private HeartbeatMonitor monitor;

        public MonitorClosedListener(HeartbeatMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
        }
    }
}
