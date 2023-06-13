package filters;

import java.io.IOException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.google.gson.JsonObject;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InputValidatorFilter implements Filter {
    private String fieldName, message;
    private String[] methods;
    private Integer[] params;

    public InputValidatorFilter(String fieldName, String[] methods) {
        this.fieldName = fieldName;
        this.methods = methods;
    }

    public InputValidatorFilter(String fieldName, Integer[] params,  String[] methods) {
        this.fieldName = fieldName;
        this.params = params;
        this.methods = methods;
    }

    // public InputValidatorFilter(String fieldName, String[] methods, String message) {
    //     this.fieldName = fieldName;
    //     this.methods = methods;
    //     this.message = message;
    // }

    // public InputValidatorFilter(String fieldName, Integer[] params, String[] methods, String message) {
    //     this.fieldName = fieldName;
    //     this.methods = methods;
    //     // this.message = message;
    //     this.params = params;
    // }

    private void setMessage(String message) {
        if(this.message == null || this.message.isEmpty()) this.message = message; 
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        JsonObject body = (JsonObject) req.getAttribute("requestBody");
        
        String value = body.get(this.fieldName) != null ? body.get(this.fieldName).getAsString() : null;
        
        Boolean valid = true;
        for(String method : methods) {
            switch(method) {
                case "mandatory":
                    if ( value == null || value.isEmpty()) {
                        setMessage(this.fieldName + " is mandatory");
                        valid = false;
                    }
                    break;
                case "isEmail":
                    if (!isEmail(value)) {
                        setMessage("Invalid email address");
                        valid = false;
                    }
                    break;
                case "length":
                    if(!length(value)) {
                        valid = false;
                    }
                    break;
            }

            if(!valid) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, this.message);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private Boolean length(String value) {
        Integer min, max = 0;
        if (this.params == null)
            return false;

        switch(this.params.length) {
            case 1:
                max = this.params[0];
                setMessage(value + "should be at least " + max +  " characters long");
                return value.length() < max;
            case 2:
                min = this.params[0];
                max = this.params[1];
                setMessage(value + "should be more than " + min +  " characters long and less than " + max);
                return value.length() > min && value.length() < max;
            default:
                return false;
        }
    }

    private Boolean isEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    
}
