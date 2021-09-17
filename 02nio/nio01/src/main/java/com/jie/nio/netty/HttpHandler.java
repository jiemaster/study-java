package com.jie.nio.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
/**
 * @author Jie.LJ.Liu
 * @date 2021/9/17 15:35
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        String uri = fullHttpRequest.uri();

        if (uri.contains("/test")) {
            handlerTest(fullHttpRequest, ctx, "hello for test");
        } else {
            handlerTest(fullHttpRequest, ctx, "hello world");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handlerTest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, String body) {
        FullHttpResponse fullHttpResponse = null;

        try {
            String value = body;
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().set("Context-Length", fullHttpResponse.content().readableBytes());
        } finally {
            if (fullHttpRequest != null) {
                ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
            } else {
                fullHttpResponse.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(fullHttpResponse);
            }
            ctx.flush();
        }
    }
}
