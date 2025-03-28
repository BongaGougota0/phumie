package za.co.phumie.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.dto.LoginCredentials;
import za.co.phumie.dto.PhumieUserDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.service.UsersService;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PhumieUserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok().body(usersService.getUserById(userId));
    }

    @GetMapping("/get")
    public ResponseEntity<Long> getUserIdByUsername(@RequestParam("username") String username){
        return ResponseEntity.ok().body(usersService.getUserByUsername(username));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials loginCredentials){

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody PhumieUserDto phumieUserDto){
        return ResponseEntity.ok().body(usersService.save(phumieUserDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody PhumieUserDto phumieUserDto){
        return ResponseEntity.ok().body(usersService.putUserDetails(phumieUserDto));
    }

    @PutMapping("/update-username/{oldUsername}")
    public ResponseEntity<ResponseDto> updateUserName(@PathVariable String oldUsername,
                                                      @RequestBody PhumieUserDto phumieUserDto){
        boolean isUpdated = usersService.putUsername(oldUsername, phumieUserDto);
        if(isUpdated){
            var response = new ResponseDto();
            response.setMessage("username updated");
            return ResponseEntity.ok().body(response);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseDto> changeUserPassword(@RequestBody PhumieUserDto phumieUserDto){
        usersService.putPassword(phumieUserDto);
        var response = new ResponseDto();
        response.setMessage("Password updated");
        return ResponseEntity.ok().body(response);
    }
}
