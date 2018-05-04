/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.strobo.sh.dao.DeviceDao;
import ru.strobo.sh.data.Device;
import ru.strobo.sh.data.KotelData;
import ru.strobo.sh.data.RoomData;
import ru.strobo.sh.beans.KotelBean;

/**
 *
 * @author k.baukov
 */
@Component
public class DeviceMessageHandler extends TextWebSocketHandler {
    
    @Autowired
    DeviceSessionsHandler sh;
    
    @Autowired
    DeviceDao dDao;
    
    @Autowired
    KotelBean kotel;
    
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DeviceMessageHandler.class);
    
    private String message ="";
 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
         Logger.info("Connection closed: " + status.getReason());
    }
 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        
        sh.addSession(session);
        Logger.info("Connection estabished: " + session.isOpen());
 
        session.sendMessage(new TextMessage("{success:true, action:connect}"));
    }
 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        
        String dId = sh.getHeaderValue(session, "deviceid");
        message += textMessage.getPayload(); 
        System.out.println(message);
        
//        String endMarker = message.substring(message.length()-3, message.length()-1);
//        System.out.println("endMarker="+endMarker);
//        if(!endMarker.equals("//")) {
//            return;
//        } else
//            message = message.substring(0, message.length()-3);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        Logger.info("Message received from " + dId +":\r\n" + message );
        
        if(message.contains("connect")) {
            
        } else if(message.contains("koteldata")){ // { "type":"koteldata", deviceId:"ESP_1C2928", "to":23.78, "tp":34.56, "kw":11, "pr":2.20 }
            KotelData data = objectMapper.readValue(message, KotelData.class);
            Logger.info( data.toString() );
            kotel.setKotelMeshData(data);
        } else if(message.contains("roomdata")){ // { "type":"roomdata", deviceId:"ESP_1C2928", "t":23.78, "h":40 }
            RoomData data = objectMapper.readValue(message, RoomData.class);
            Logger.info( data.toString() );
            kotel.setRoomsData(data);
        } else if(message.contains("getDevices")){
            String data = "";
            List<Device> devices = dDao.getDevices();

            for(Device d : devices)
                data += ", { \"name\":"+d.getName()+", \"ip\":\""+d.getIp()+"\", \"type\":\""+d.getType()+"\" }";
            
            session.sendMessage(new TextMessage("{ \"success\": true, \"data\":[" + data.substring(1) + " ] }"));
        } else {
            
        }
            
        message = "";
        
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
         Logger.error("error occured at sender " + session + ";  reason: " + throwable.getMessage());
    }

}
