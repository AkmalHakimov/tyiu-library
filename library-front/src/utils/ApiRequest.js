import axios from "axios";
import { ErrorNotify } from "./ErrorNotify/ErrorNotify";

export const BASE_URL = "http://localhost:8080/api";

const ApiRequest = axios.create({
    baseURL: BASE_URL,
    timeout: 6000
})

ApiRequest.interceptors.request.use(
    (config)=>{
        let x = localStorage.getItem("access_token")
        if(x){
            config.headers.Authorization = `Bearer ${x}`
        }
        return config
    },
    (error)=>{
        return Promise.reject(error)
    }
)

ApiRequest.interceptors.response.use((response) => {
    return response
}, (error) => {
    switch (error.response?.status) {
        case 401:
          ErrorNotify(error.response.data);
          break;
        case 404:
          ErrorNotify(error.response.data);
          break;
        case 403:
          ErrorNotify("You don't have permission");
          break;
        case 500:
          ErrorNotify('Serverda xatolik yuz berdi');
          break;
        default:
          // Handle other status codes as needed
          break;
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




