package gash.router.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import pipe.work.Work;


/**
 * Created by sam on 3/25/17.
 */
public class MonitorInitializer extends ChannelInitializer<SocketChannel>{
    private WorkHandler handler;

    public MonitorInitializer(WorkHandler hbh){
        this.handler=hbh;
    }
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline=channel.pipeline();
        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(67108864, 0, 4, 0, 4));

        // pipeline.addLast("frameDecoder", new
        // DebugFrameDecoder(67108864, 0, 4, 0, 4));

        // decoder must be first
        pipeline.addLast("protobufDecoder", new ProtobufDecoder(Work.Heartbeat.getDefaultInstance()));
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());

        // our server processor (new instance for each connection)
        pipeline.addLast("handler", handler);
    }
}
