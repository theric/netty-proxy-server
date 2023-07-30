package cc.leevi.common.httpproxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DirectClientHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(DirectClientHandler.class);

    private final Promise<Channel> promise;

    public DirectClientHandler(Promise<Channel> promise) {
        logger.info(" direct client handler constuction");
        this.promise = promise;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info(" direct client handler channel active");
        ctx.pipeline().remove(this);
        promise.setSuccess(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        logger.info(" direct client handler  exception caught");
        promise.setFailure(throwable);
    }
}