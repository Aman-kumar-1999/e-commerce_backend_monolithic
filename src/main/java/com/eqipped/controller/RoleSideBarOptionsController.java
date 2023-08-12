package com.eqipped.controller;

import com.eqipped.entities.RoleSideBarOptions;
import com.eqipped.repositories.RoleSideBarOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roleSideBarOptions")
@CrossOrigin("*")
public class RoleSideBarOptionsController {

    @Autowired
    private RoleSideBarOptionsRepository roleSideBarOptionsRepository;

    @PostMapping("/")
    public ResponseEntity<?> createRoleSideBarOptions(@RequestBody RoleSideBarOptions roleSideBarOptions){
        Map<String,Object> map = new HashMap<>();
        try{
            RoleSideBarOptions roleSideBarOptions1 = roleSideBarOptionsRepository.save(roleSideBarOptions);
            map.put("STATUS","SUCCESS");
            map.put("ROLE_SIDE_BAR",roleSideBarOptions1);
        }catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role Side Bar Options can't be created");

        }
        return ResponseEntity.ok(map);
    }
    @PutMapping("/")
    public ResponseEntity<?> updateRoleSideBarOptions(@RequestBody RoleSideBarOptions roleSideBarOptions){
        Map<String,Object> map = new HashMap<>();
        try{
            RoleSideBarOptions roleSideBarOptions2 = new RoleSideBarOptions();
            if(roleSideBarOptions.getCart()!=null || !roleSideBarOptions.getCart().isBlank() || !roleSideBarOptions.getCart().isEmpty())
                roleSideBarOptions2.setCart(roleSideBarOptions.getCart());
            if(roleSideBarOptions.getDashboard()!=null || !roleSideBarOptions.getDashboard().isBlank() || !roleSideBarOptions.getDashboard().isEmpty())
                roleSideBarOptions2.setDashboard(roleSideBarOptions.getDashboard());
            if(roleSideBarOptions.getHome()!=null || !roleSideBarOptions.getHome().isBlank() || !roleSideBarOptions.getHome().isEmpty())
                roleSideBarOptions2.setHome(roleSideBarOptions.getHome());
            if(roleSideBarOptions.getOrder()!=null || !roleSideBarOptions.getOrder().isBlank() || !roleSideBarOptions.getOrder().isEmpty())
                roleSideBarOptions2.setOrder(roleSideBarOptions.getOrder());
            if(roleSideBarOptions.getMyOder()!=null || !roleSideBarOptions.getMyOder().isBlank() || !roleSideBarOptions.getMyOder().isEmpty())
                roleSideBarOptions2.setMyOder(roleSideBarOptions.getMyOder());
            if(roleSideBarOptions.getWishlist()!=null || !roleSideBarOptions.getWishlist().isBlank() || !roleSideBarOptions.getWishlist().isEmpty())
                roleSideBarOptions2.setWishlist(roleSideBarOptions.getWishlist());
            if(roleSideBarOptions.getNotification()!=null || !roleSideBarOptions.getNotification().isBlank() || !roleSideBarOptions.getNotification().isEmpty())
                roleSideBarOptions2.setNotification(roleSideBarOptions.getNotification());
            if(roleSideBarOptions.getOthers()!=null || !roleSideBarOptions.getOthers().isBlank() || !roleSideBarOptions.getOthers().isEmpty())
                roleSideBarOptions2.setOthers(roleSideBarOptions.getOthers());
            if(roleSideBarOptions.getProduct()!=null || !roleSideBarOptions.getProduct().isBlank() || !roleSideBarOptions.getProduct().isEmpty())
                roleSideBarOptions2.setProduct(roleSideBarOptions.getProduct());
            if(roleSideBarOptions.getProfile()!=null || !roleSideBarOptions.getProfile().isBlank() || !roleSideBarOptions.getProfile().isEmpty())
                roleSideBarOptions2.setProfile(roleSideBarOptions.getProfile());
            if(roleSideBarOptions.getSetting()!=null || !roleSideBarOptions.getSetting().isBlank() || !roleSideBarOptions.getSetting().isEmpty())
                roleSideBarOptions2.setSetting(roleSideBarOptions.getSetting());
            if(roleSideBarOptions.getUser()!=null || !roleSideBarOptions.getUser().isBlank() || !roleSideBarOptions.getUser().isEmpty())
                roleSideBarOptions2.setUser(roleSideBarOptions.getUser());
            if(roleSideBarOptions.getVendor()!=null || !roleSideBarOptions.getVendor().isBlank() || !roleSideBarOptions.getVendor().isEmpty())
                roleSideBarOptions2.setVendor(roleSideBarOptions.getVendor());
            if(roleSideBarOptions.getShipments()!=null || !roleSideBarOptions.getShipments().isBlank() || !roleSideBarOptions.getShipments().isEmpty())
                roleSideBarOptions2.setShipments(roleSideBarOptions.getShipments());


            RoleSideBarOptions roleSideBarOptions1 = roleSideBarOptionsRepository.save(roleSideBarOptions2);
            map.put("STATUS","SUCCESS");
            map.put("ROLE_SIDE_BAR",roleSideBarOptions1);
        }catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role Side Bar Options can't be updated");

        }
        return ResponseEntity.ok(map);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllRoleSideBarOptions(){
        Map<String,Object> map = new HashMap<>();
        try{
            List<RoleSideBarOptions> roleSideBarOptions1 = roleSideBarOptionsRepository.findAll();
            map.put("STATUS","SUCCESS");
            map.put("ROLE_SIDE_BARS",roleSideBarOptions1);
        }catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role Side Bar Options can't be find");

        }
        return ResponseEntity.ok(map);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getRoleSideBarOptions(@PathVariable String id){
        Map<String,Object> map = new HashMap<>();
        try{
            RoleSideBarOptions roleSideBarOptions1 = roleSideBarOptionsRepository.findById(id).get();
            if(roleSideBarOptions1 != null){
                map.put("STATUS","SUCCESS");
                map.put("ROLE_SIDE_BAR",roleSideBarOptions1);
            }

        }catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role Side Bar Options can't be find");

        }
        return ResponseEntity.ok(map);
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteRoleSideBarOptions(@PathVariable String id){
        Map<String,Object> map = new HashMap<>();
        try{
            roleSideBarOptionsRepository.deleteById(id);

                map.put("STATUS","SUCCESS");
                map.put("MSG"," Role Side Bar has been deleted whose Id : "+id);

        }catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role Side Bar Options can't be deleted whose Id : "+id);

        }
        return ResponseEntity.ok(map);
    }
}
