package gash.router.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sam on 3/25/17.
 */
public class HeartbeatHandler extends SimpleChannelInboundHandler{

    protected static Logger logger = LoggerFactory.getLogger("cmd");

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("get heartbeat from before");
    }
}
