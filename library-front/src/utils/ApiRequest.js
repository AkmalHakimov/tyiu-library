import axios from "axios";
import { ErrorNotify } from "./ErrorNotify/ErrorNotify";

const BASE_URL = "http://localhost:8080/api";

const ApiRequest = axios.create({
    baseURL: BASE_URL,
    timeout: 6000
})

ApiRequest.interceptors.request.use(
    (config)=>{
        return config
    },
    (error)=>{
        return Promise.reject(error)
    }
)

ApiRequest.interceptors.response.use((response) => {
    return response
}, (error) => {
    console.log(error);
    console.log("salom");
    // let notificationParam = {message: error.message};

    // Remove token and redirect
    if (error.response.status === 401 || error.response.status === 403) {
        
    }

    if (error.response.status === 404) {
        // console.log("404 error");
    }


    return Promise.reject(error);
}
)
export default ApiRequest;
  

// const ApiRequest = (url, method, data) => {
//   return axios({
//     baseURL: BASE_URL,
//     url: "/api" + url,
//     method,
//     data,
//   })
//     .then((response) => response)
//     .catch((err) => {
//         // console.log(err);
//         if(err.response.status == 404){
//             console.log("salom");
//         }
//     });
// };

// export default ApiRequest;




