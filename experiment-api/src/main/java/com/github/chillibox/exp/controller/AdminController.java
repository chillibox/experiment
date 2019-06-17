package com.github.chillibox.exp.controller;

import com.github.chillibox.exp.entity.SysUser;
import com.github.chillibox.exp.repository.SysUserRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created on 2017/7/4.</p>
 *
 * @author Gonster
 */

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    public static final String USER_FILTER = "user";


    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String ADMIN_USERNAME;

    /* ------ pages ------ */

    @RequestMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping({"/dashboard", ""})
    public String dashboardPage(Principal principal, ModelMap modelMap) {
        return "admin/dashboard";
    }

    /* ------ API ------ */
    /* ------ profile ------ */

    @GetMapping("/api/profile")
    @ResponseBody
    public SysUser findProfile(Principal principal) {
        SysUser sysUser = sysUserRepository.findByUsernameOrEmail(principal.getName());
        if (sysUser == null)
            return null;
        SysUser dto = new SysUser(sysUser);
        dto.setPassword(null);
        return dto;
    }

    @PostMapping("/api/profile")
    @ResponseBody
    public void changeProfile(Principal principal, @RequestBody SysUser user) {
        SysUser sysUser = sysUserRepository.findByUsernameOrEmail(principal.getName());

        String password = user.getPassword();
        if (Strings.isNullOrEmpty(password)) {
            user.setPassword(sysUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setUsername(sysUser.getUsername());
        user.setRole(sysUser.getRole());
        user.setId(sysUser.getId());
        sysUserRepository.save(user);
    }


    /* ------ user ------ */
    @GetMapping("/api/user")
    @ResponseBody
    public List<SysUser> findUser() {
        Iterable<SysUser> all = sysUserRepository.findAll();
        List<SysUser> sysUserList = new ArrayList<>();
        all.forEach(user -> {
            SysUser dto = new SysUser(user);
            dto.setPassword(null);
            sysUserList.add(dto);
        });
        return sysUserList;
    }

    @PostMapping(value = "/api/user")
    public ResponseEntity<SysUser> saveUser(@RequestBody SysUser user) {
        String password = user.getPassword();
        String username = user.getUsername();
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            return ResponseEntity.badRequest().build();
        }

        if (ADMIN_USERNAME.equalsIgnoreCase(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        SysUser sysUser = sysUserRepository.findByUsernameOrEmail(username);
        if (sysUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        try {
            user.setPassword(passwordEncoder.encode(password));
            user.setId(null);
            user = sysUserRepository.save(user);

            SysUser dto = new SysUser(user);
            dto.setPassword(null);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/api/user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") Long id,
                                           @RequestBody SysUser user) {
        if (!sysUserRepository.exists(id))
            return ResponseEntity.notFound().build();

        SysUser sysUser = sysUserRepository.findOne(id);

        String password = user.getPassword();
        if (Strings.isNullOrEmpty(password)) {
            user.setPassword(sysUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setUsername(sysUser.getUsername());
        user.setId(id);
        sysUserRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/api/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (!sysUserRepository.exists(id))
            return ResponseEntity.notFound().build();

        sysUserRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
