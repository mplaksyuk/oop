package abstr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class ControllerAbstract extends HttpServlet {
    private static final Logger log = Logger.getLogger(ControllerAbstract.class);
    
    private Boolean callback_colled = false;

    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
    }

    public void callFilters(Filter[] filters) throws IOException, ServletException {
        FiltersChain chain = new FiltersChain(filters);

        chain.doFilter(this.req, this.resp);
    }

    protected void getMapping(String regex, Callback callback, Filter... filters) throws IOException, ServletException {
        String pathInfo = Objects.toString(this.req.getPathInfo(), "");
        
        if (Pattern.matches(regex, pathInfo)) {

            List<String> m = new ArrayList<String>();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(pathInfo);

            while(matcher.find())
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    m.add(matcher.group(i));
                }

            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                sb.append(line);

            String requestBody = sb.toString();

            Gson gson = new Gson();
            JsonObject r = gson.fromJson(requestBody, JsonObject.class);

            req.setAttribute("requestBody", r);
            
            this.callback_colled = true;

            callFilters(filters);
            
            if(resp.getStatus() == 200) {
                callback.onResult(m, r);
            }
        }
    }

    protected void responseError() throws ServletException, IOException {
        if(!callback_colled)
            responseError(HttpServletResponse.SC_NOT_FOUND, getServletInfo());
    }

    protected void responseError(Integer statusCode, String message){
        try {
            this.resp.sendError(statusCode, message);   
        } catch (Exception e) {
            log.info(e);
        }
            
    }


    protected interface Callback {
        void onResult(List<String> m, JsonObject r);
    }
}


class FiltersChain implements FilterChain {

    private Filter[] filters;
    private int currentFilterIndex;

    public FiltersChain(Filter[] filters) {
        this.filters = filters;
        this.currentFilterIndex = 0;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (this.currentFilterIndex < this.filters.length) {
            Filter currentFilter = this.filters[currentFilterIndex++];
            currentFilter.doFilter(request, response, this);
        }
    }

}

