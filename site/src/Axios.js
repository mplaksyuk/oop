import a from "axios"

const baseurl = "http://localhost:3002/restaurant/"

const Axios = a.create({
    baseURL : baseurl
})

Axios.interceptors.request.use(config=> {
    const accessToken = localStorage.getItem("accessToken");

    if(accessToken && !config.headers["Refresh"])
        config.headers["Authorization"] = `Bearer ${accessToken}`;
    
    return config;
});

Axios.interceptors.response.use((response) => response,
    (error) => {
        const { response, config } = error;

        if (response.status === 401) {
            let refreshToken = localStorage.getItem("refreshToken");
            
            if (refreshToken) {
                const refreshConfig = {
                    headers: {
                        "Refresh": `Bearer ${refreshToken}`
                    }
                }
                console.log('refresh tokens');

                const tokens = Axios.post(`${baseurl}tokens/refresh`, {}, refreshConfig).then((tokens) => {
                    console.log('refreshed tokens !!!!!');
                    let newAccessToken = tokens.data.accessToken;
                    let newRefreshToken = tokens.data.refreshToken;
    
                    if (newAccessToken && newRefreshToken) {
                        localStorage.setItem("accessToken", newAccessToken);
                        localStorage.setItem("refreshToken", newRefreshToken);
    
                        config.headers["Authorization"] = `Bearer ${newAccessToken}`;
                        return Axios(config);
                    }
                    return Promise.reject(error);
                })
            }
        }
    }
);

export default Axios;
