package com.eqipped.entities;


//import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Optional;

@Document
@Collation("role")
public class Role {

    @Id
    private String roleId;
    private String roleName;

    private String roleSideBarId;


    private Optional<RoleSideBarOptions> roleSideBarOptions;

    public Role() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleSideBarId() {
        return roleSideBarId;
    }

    public void setRoleSideBarId(String roleSideBarId) {
        this.roleSideBarId = roleSideBarId;
    }

    public Optional<RoleSideBarOptions> getRoleSideBarOptions() {
        return roleSideBarOptions;
    }

    public void setRoleSideBarOptions(Optional<RoleSideBarOptions> roleSideBarOptions) {
        this.roleSideBarOptions = roleSideBarOptions;
    }



//    @Override
//    public String toString() {
//        return "Role{" +
//                "roleId='" + roleId + '\'' +
//                ", roleName='" + roleName + '\'' +
//                ", roleSideBarOptions=" + roleSideBarOptions +
//                '}';
//    }
}
