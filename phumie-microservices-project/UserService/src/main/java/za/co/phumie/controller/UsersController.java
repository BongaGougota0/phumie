package za.co.phumie.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.dto.PhumieUserDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.service.UsersServiceImpl;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
    private final UsersServiceImpl usersServiceImpl;

    public UsersController(UsersServiceImpl usersServiceImpl) {
        this.usersServiceImpl = usersServiceImpl;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PhumieUserDto> getUserById(@PathVariable("userId") Long userId){
        return ResponseEntity.ok().body(usersServiceImpl.getUserById(userId));
    }

    @GetMapping()
    public ResponseEntity<Long> getUserIdByUsername(@RequestParam("username") String username){
        return ResponseEntity.ok().body(usersServiceImpl.getUserByUsername(username));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody PhumieUserDto phumieUserDto){
        return ResponseEntity.ok().body(usersServiceImpl.putUserDetails(phumieUserDto));
    }

    @PutMapping()
    public ResponseEntity<ResponseDto> updateUserName(@RequestParam("oldUsername") String oldUsername,
                                                      @RequestBody PhumieUserDto phumieUserDto){
        boolean isUpdated = usersServiceImpl.putUsername(oldUsername, phumieUserDto);
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
        usersServiceImpl.putPassword(phumieUserDto);
        var response = new ResponseDto();
        response.setMessage("Password updated");
        return ResponseEntity.ok().body(response);
    }
}
