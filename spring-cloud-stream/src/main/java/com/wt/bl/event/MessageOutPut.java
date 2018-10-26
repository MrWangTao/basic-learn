package com.wt.bl.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author WangTao
 *         Created at 18/9/27 下午2:20.
 */
public interface MessageOutPut {

    String message = "message-out-put";

    @Output(MessageOutPut.message)
    MessageChannel output(String message);

}
