package za.co.phumie.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.dto.PhumieUserDto;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserByUsernameEmail(){
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/post")
    public ResponseEntity<?> registerUser(@RequestBody PhumieUserDto phumieUserDto){
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody PhumieUserDto phumieUserDto){
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/update-username")
    public ResponseEntity<?> updateUserName(@RequestBody PhumieUserDto phumieUserDto){
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changeUserPassword(@RequestBody PhumieUserDto phumieUserDto){
        return ResponseEntity.ok().body(null);
    }
}
