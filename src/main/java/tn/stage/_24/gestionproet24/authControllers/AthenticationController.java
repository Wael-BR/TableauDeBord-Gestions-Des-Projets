package tn.stage._24.gestionproet24.authControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import tn.stage._24.gestionproet24.config.jwtConfig.JwtService;
import tn.stage._24.gestionproet24.dao.AthenticationRequest;
import tn.stage._24.gestionproet24.dao.AuthenticationResponse;
import tn.stage._24.gestionproet24.dao.LoginRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/Athentication")
public class AthenticationController {
    private final AuthenticationService authenticationService;

    private JwtService jwtService;
    private UserDetailsService userDetailsService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AthenticationRequest req
    ){
        return ResponseEntity.ok(authenticationService.register(req));

    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest req
    ){
        return ResponseEntity.ok(authenticationService.authenticate(req));

    }

    @PostMapping("/authenticateByEmail")
    public ResponseEntity<AuthenticationResponse> authenticateByEmail(
            @RequestBody LoginRequest req
    ){
        return ResponseEntity.ok(authenticationService.authenticateByEmail(req));

    }


}
