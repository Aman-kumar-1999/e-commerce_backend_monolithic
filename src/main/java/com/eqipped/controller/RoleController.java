package com.eqipped.controller;

import com.eqipped.entities.Role;
import com.eqipped.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/role")
@CrossOrigin("*")
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @PostMapping("/")
    public Map<String,Object> createRole(@RequestBody Role role){
        Map<String,Object> map = new HashMap<>();
        try {
            Role role1 = repository.save(role);
            map.put("STATUS","SUCCESS");
            map.put("ROLE", role1);
            System.out.println("Role : " + role1.toString());

        }
        catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role can't be Created ");
        }
        return map;
    }
    @PutMapping("/")
    public Map<String,Object> updateRole(@RequestBody Role role){
        Map<String,Object> map = new HashMap<>();
        try {
            Role role2 = repository.findById(role.getRoleId()).get();

            if(role2!=null){
                if (role.getRoleName()!=null)
                    role2.setRoleName(role.getRoleName());
                if (!role.getRoleSideBarId().isEmpty() || !role.getRoleSideBarId().isBlank() || role.getRoleSideBarId()!=null)
                    role2.setRoleSideBarId(role.getRoleSideBarId());
                Role role1 = repository.save(role2);
                map.put("STATUS","SUCCESS");
                map.put("ROLE", role1);
                System.out.println("Role : " + role1.toString());
            }


        }
        catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role can't be Updated ");
        }
        return map;
    }
    @GetMapping("/")
    public Map<String,Object> getAllRole(){
        Map<String,Object> map = new HashMap<>();
        try {
            List<Role> role1 = repository.findAll();
            map.put("STATUS","SUCCESS");
            map.put("ROLES", role1);
            System.out.println("Roles : " + role1.toString());

        }
        catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role can't be find ");
        }
        return map;
    }
    @GetMapping("/{id}")
    public Map<String,Object> findById(@PathVariable String id){
        Map<String,Object> map = new HashMap<>();
        try {
            Role role1 = repository.findById(id).get();
            map.put("STATUS","SUCCESS");
            map.put("ROLE", role1);


        }
        catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role can't be find ");
        }
        return map;
    }
    @DeleteMapping("/{id}")
    public Map<String,Object> deletedById(@PathVariable String id){
        Map<String,Object> map = new HashMap<>();
        try {
            repository.deleteById(id);
            map.put("STATUS","SUCCESS");
            map.put("ROLE", "Role has been deleted whose Id : "+id);


        }
        catch (Exception e){
            e.printStackTrace();
            map.put("STATUS","FAILED");
            map.put("MSG","Sorry ! Role can't be deleted whose Id : "+id);
        }
        return map;
    }

}
