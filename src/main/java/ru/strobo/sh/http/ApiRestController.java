/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.strobo.sh.http;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import ru.strobo.sh.beans.KotelBean;
import ru.strobo.sh.dao.DeviceDao;
import ru.strobo.sh.dao.KotelDao;
import ru.strobo.sh.dao.UserDao;
import ru.strobo.sh.data.Device;
import ru.strobo.sh.data.User;

/**
 *
 * @author k.baukov
 */
@RestController
@RequestMapping("/api")
public class ApiRestController {
    
    @Autowired
    KotelBean kotel;
    
    @Autowired
    UserDao uDao;
    
    @Autowired
    DeviceDao dDao;
    
    @Autowired
    KotelDao kDao;
    
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ApiRestController.class);
    
    @RequestMapping(value = "/login", method = GET,  produces = "application/json;charset=UTF-8" )
    public String login(
            @RequestParam(value="login", required = true) String login,
            @RequestParam(value="pass",  required = true) String pass,
            HttpServletRequest   request
    ) {
        HttpSession sess =  request.getSession();
        Object Auth = sess.getAttribute("Auth");
        
        User user = uDao.auth(login, pass);
        if(user!=null) {
            String sessionId = user.getSessionId();
            Auth = "true";
            sess.setAttribute("Auth", Auth);
            sess.setAttribute("User", user);
            sess.setAttribute("SessID", sessionId);
            
            return "{success:true,sess:\"JSESSIONID=" + sess.getId() + "\"}";
        } else {
            return "{success:false,msg:\"Вдоступе отказано\"}";
        }
    }
    
    @RequestMapping(value = "/users", method = GET,  produces = "application/json;charset=UTF-8" )
    public String getUsers( ) {
        String data = "";        
        List<User> users = uDao.getUsers();
        
        for(User u : users)
            data += "," + u.toJson();
        
        return "{ success: true, data:[" + data.substring(1) + "]}";
    }
    
    @RequestMapping(value = "/devices", method = GET,  produces = "application/json;charset=UTF-8" )
    public String getDevices() {
        String data = "";
        List<Device> devices = dDao.getDevices();
        
        for(Device d : devices)
            data += "," + d.toJson();
        
        return "{ success: true, data:[" + data.substring(1) + "]}";
    }
    
//    @RequestMapping(value = "/devlist", method = GET,  produces = "application/json;charset=UTF-8" )
//    public String getDevList() {
//        String data = "";
//        List<Device> devices = dDao.getDevices();
//        
//        for(Device d : devices)
//            data += "," + d.toJson();
//        
//        return "{ success: true, data: [ " + data.substring(1) + " ] }";
//    }
    
    @RequestMapping(value = "/kotel/setvalues", method = GET,  produces = "application/json;charset=UTF-8" )
    public String ctrl(
//            @RequestParam(value="desttp", defaultValue="0", required = true) String destTp,
//            @RequestParam(value="destto", defaultValue="0", required = true) String destTo,
//            @RequestParam(value="destkw", defaultValue="0", required = true) String destKw,
            @RequestParam(value="tp", defaultValue="0" , required = true) String tp,
            @RequestParam(value="to", defaultValue="0", required = true) String to,
            @RequestParam(value="kw", defaultValue="0", required = true) String kw
    ) {
        Logger.info("Incoming http request: /ctrl: "
//                + "{desttp=" + destTp 
//                + ",destto=" + destTo 
//                + ",destkw=" + destKw 
                + ",tp=" + tp + ", to=" + to+ ", kw=" + kw
        + "}");
        
        kotel.setTp(Float.valueOf(tp));
        kotel.setTo(Float.valueOf(to));        
        kotel.setKw(Integer.valueOf(kw));   
        //kotel.setDestTp(Float.valueOf(destTp));
        //kotel.setDestTo(Float.valueOf(destTo));        
        //kotel.setDestKw(Integer.valueOf(destKw)); 
        
        String comm = kotel.getControlCommand();
        kotel.setControlCommand("");
        return "{success:true"
                + ",destTp:" + kotel.getDestTp()
                + ",destTo:" + kotel.getDestTo()
                + ",destTc:" + kotel.getDestTo()
                + ",destKw:" + kotel.getDestKw()
                + ",comm:\"" + comm +"\""
                + ",wait:" + kotel.getWait()
        + "}";
    }
    
    @RequestMapping(value = "/rooms/setvalues", method = GET,  produces = "application/json;charset=UTF-8" )
    public String ctrlCommand(
            @RequestParam(value="t1", defaultValue="0", required = true) String t1,
            @RequestParam(value="t2", defaultValue="0" , required = true) String t2,
            @RequestParam(value="t3", defaultValue="0", required = false) String t3,
            @RequestParam(value="h1", defaultValue="0", required = true) String h1,
            @RequestParam(value="h2", defaultValue="0" , required = true) String h2,
            @RequestParam(value="h3", defaultValue="0", required = false) String h3
    ) {
        Logger.info("Incoming http request: /rooms/setvalues: {"
                + "t1=" + t1 + ", t2=" + t2 + ", t3=" + t3 
                + ",h1=" + h1 + ", h2=" + h2 + ", h3=" + h3 
        + "}");
        
        kotel.setT1(Float.valueOf(t1));
        kotel.setT2(Float.valueOf(t2));    
        kotel.setT3(Float.valueOf(t3));    
        kotel.setH1(Float.valueOf(h1));
        kotel.setH2(Float.valueOf(h2));    
        kotel.setH3(Float.valueOf(h3));    
        
        return "{success:true}";
    }
    
    @RequestMapping(value = "/ctrlc", method = GET,  produces = "application/json;charset=UTF-8" )
    public String ctrlCommand(
            @RequestParam(value="destt", defaultValue="0", required = true) String destT,
            @RequestParam(value="tp", defaultValue="0" , required = true) String tp,
            @RequestParam(value="to", defaultValue="0", required = false) String to,
            @RequestParam(value="t1", defaultValue="0", required = false) String t1
    ) {
        Logger.info("Incoming http request: /ctrlc: {dest=" + destT + ", tp=" + tp + ", to=" + to + ", t1=" + t1 + "}");
        
        kotel.setTp(Float.valueOf(tp));
        kotel.setTo(Float.valueOf(to));    
        kotel.setT1(Float.valueOf(t1));    
        String comm = kotel.getControlCommand();
        kotel.setControlCommand("");
        return "{success:true,"
                + "destT:"+ kotel.getDestTp()+","
                + "comm:\"" + comm + "\""
                + "}";
    }
    
    @RequestMapping(value = "/setdestt", method = GET,  produces = "application/json;charset=UTF-8" )
    public String setDestT(
            @RequestParam(value="desttp", required = false) String destTp,
            @RequestParam(value="destto", required = false) String destTo,
            @RequestParam(value="desttc", required = false) String destTc,
            @RequestParam(value="destkw", required = false) String destKw
    ) {
        if(destTp!=null) kotel.setDestTp(Float.valueOf(destTp));
        if(destTo!=null) kotel.setDestTo(Float.valueOf(destTo));
        if(destTc!=null) kotel.setDestTc(Float.valueOf(destTc));
        if(destKw!=null) kotel.setDestKw(Integer.valueOf(destKw));
        
        kDao.setAllSetings();
        
        Logger.info("Destination values setted in [ " 
                  + "destTp:" + kotel.getDestTp()
                + " ,destTo:" + kotel.getDestTo()
                + " ,destTc:" + kotel.getDestTc()
                + " ,destKw:" + kotel.getDestKw()
        +" ]");
        
        return "{success:true}";
    }
    
    @RequestMapping(value = "/setcomm", method = GET,  produces = "application/json;charset=UTF-8" )
    public String setComm(
            @RequestParam(value="comm", required = true) String comm
    ) {
        kotel.setControlCommand(comm);
        Logger.info("Control command setted in " + comm);
        return "{success:true}";
    }
    
    @RequestMapping(value = "/getvalues", method = GET,  produces = "application/json;charset=UTF-8" )
    public String getValues( ) {        
        String tp = String.valueOf(kotel.getTp());
        String to = String.valueOf(kotel.getTo());
        String t1 = String.valueOf(kotel.getT1());
        String t2 = String.valueOf(kotel.getT2());
        String t3 = String.valueOf(kotel.getT3());
        String h1 = String.valueOf(kotel.getH1());
        String h2 = String.valueOf(kotel.getH2());
        String h3 = String.valueOf(kotel.getH3());
        String kw = String.valueOf(kotel.getKw());
        String destTp = String.valueOf(kotel.getDestTp());
        String destTo = String.valueOf(kotel.getDestTo());
        String destTc = String.valueOf(kotel.getDestTc());
        String destKw = String.valueOf(kotel.getDestKw());
        return "{success:true"
                + ",tp:" + tp + ",to:" + to + ",kw:" + kw 
                + ",t1:" + t1 + ",t2:" + t2 + ",t3:" + t3
                + ",h1:" + h1 + ",h2:" + h2 + ",h3:" + h3
                + ",desttp:" + destTp + ",destto:" + destTo + ",desttc:" + destTc + ",destkw:" + destKw 
        + "}";
    }
    
}
