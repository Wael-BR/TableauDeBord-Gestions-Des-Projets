package tn.stage._24.gestionproet24.authControllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.stage._24.gestionproet24.config.jwtConfig.JwtService;
import tn.stage._24.gestionproet24.dao.AthenticationRequest;
import tn.stage._24.gestionproet24.dao.AuthenticationResponse;
import tn.stage._24.gestionproet24.dao.LoginRequest;
import tn.stage._24.gestionproet24.entities.Role;
import tn.stage._24.gestionproet24.entities.User;
import tn.stage._24.gestionproet24.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(AthenticationRequest req) {
        Boolean a= Boolean.FALSE;
        if (req.getRole()== Role.ADMIN){
            a= Boolean.FALSE;

        }
        var user = User.builder()
                .nom(req.getNom())
                .grade(req.getGrade())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .role(req.getRole())
                .worken(a.booleanValue())
                .build();
        System.out.println(req.getUsername() +"\n");
        System.out.println(req.getEmail() +"\n");

        userRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        // if (req.getRole()==Role.ADMIN){
        return new AuthenticationResponse().builder().msg("waiting for admin acceptation").build();
        // }else {
        //   return new AuthenticationResponse().builder().token(jwtToken).role(user.getRole()).username(user.getUsername()).msg("operation affected with success").build();

        //}



    }

    public AuthenticationResponse authenticate(LoginRequest req) {
        System.out.println(passwordEncoder.encode(req.getPassword()));
        System.out.println(req.getUsername());
        System.out.println(req.getEmail());
        System.out.println(req.getEmail() +"**********     1       **************" + req.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )

        );
        System.out.println(req.getEmail() +"**********     1       **************" + req.getPassword());

        var user=userRepository.findUserByUsername(req.getUsername())

                .orElseThrow();
        if (passwordEncoder.matches(req.getPassword(), user.getPassword())){
            if (user.getWorken()){


                var jwtToken=jwtService.generateToken(user);
                return new AuthenticationResponse().builder().token(jwtToken).role(user.getRole()).username(user.getUsername()).msg("operation affected with success").build();
            }else {
                return new AuthenticationResponse().builder().msg("Your account is blocked you can consult an admin to solve your error").build();

            }
        }
        return null
                ;


    }

    public AuthenticationResponse authenticateByEmail(LoginRequest req) {
        System.out.println(passwordEncoder.encode(req.getPassword()));
        System.out.println(req.getEmail());

        System.out.println(req.getEmail() +"**********     1       **************" + req.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )

        );
        System.out.println(req.getEmail() +"**********      2       **************" + req.getPassword());

        var user=userRepository.findUserByEmail(req.getEmail())

                .orElseThrow();
        if (passwordEncoder.matches(req.getPassword(), user.getPassword())){
            if (user.getWorken()){


                var jwtToken=jwtService.generateToken(user);
                return new AuthenticationResponse().builder().token(jwtToken).role(user.getRole()).email(user.getEmail()).msg("operation affected with success").build();
            }else {
                return new AuthenticationResponse().builder().msg("Your account is blocked you can consult an admin to solve your error").build();

            }
        }
        return null
                ;


    }

}
