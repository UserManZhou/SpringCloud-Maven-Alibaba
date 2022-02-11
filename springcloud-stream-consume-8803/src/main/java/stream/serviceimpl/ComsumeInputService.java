package stream.serviceimpl;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@EnableBinding(Sink.class)
public class ComsumeInputService {

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("message.getPayload().toString() = " + message.getPayload().toString());
    }

}
