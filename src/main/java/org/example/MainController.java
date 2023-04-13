package org.example;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Base64;

import static java.lang.String.*;

@Controller
public class MainController {

    private static final String QR_CODE_IMAGE_PATH="src/main/resources/static/img/QRCode.png";
    @GetMapping("/")
    public String getQRCode(Model model){
        String ssid="test";
        String password="test";
        String wifiString="WIFI:T:WPA;S:%s;P:%s;;";
        wifiString=format(wifiString,ssid,password);

        byte[] image=new byte[0];

        try {
            // Generate and Return Qr Code in Byte Array
            image=QRCodeGenerator.getQRCodeImage(wifiString,250,250);
            // Generate and Save Qr Code Image in static/image folder
            QRCodeGenerator.generateQRCodeImage(wifiString,250,250,QR_CODE_IMAGE_PATH);

        }catch (WriterException | IOException e){
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrCode= Base64.getEncoder().encodeToString(image);

        model.addAttribute("wifiString",wifiString);
        model.addAttribute("qrcode",qrCode);
        return "qrcode";
    }

}
