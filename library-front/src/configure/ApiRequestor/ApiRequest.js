import axios from "axios";
import { ErrorNotify } from "../../utils/ErrorNotify/ErrorNotify";
import AxiosInterSeptor from "../AxiosInterceptor/AxiosInterSeptor"
export const BASE_URL = "http://localhost:8080/";

// const ApiRequest = axios.create({
//     baseURL: BASE_URL,
//     timeout: 6000
// })

// ApiRequest.interceptors.request.use(
//     (config)=>{
//         let x = localStorage.getItem("access_token")
//         if(x){
//             config.headers.Authorization = `Bearer ${x}`
//         }
//         return config
//     },
//     (error)=>{
//         return Promise.reject(error)
//     }
// )


// ApiRequest.interceptors.response.use((response) => {
//     return response
// }, (error) => {
//     switch (error.response?.status) {
//         case 401:
//           ErrorNotify(error.response.data);
//           break;
//         case 404:
//           ErrorNotify(error.response.data);
//           break;
//         case 403:
//           ErrorNotify("You don't have permission");
//           break;
//         case 500:
//           ErrorNotify('Serverda xatolik yuz berdi');
//           break;
//         default:
//           // Handle other status codes as needed
//           break;
//       }
//     return Promise.reject(error);
// }
// )
// export default ApiRequest;
  

export default function ApiRequest (url, method, data) {

  let x = localStorage.getItem("access_token")
  return AxiosInterSeptor({
    baseURL: BASE_URL,
    url: "/api" + url,
    method,
    data,
    headers:{
      "Authorization": `Bearer ${x}`
  }
    }).catch((err) => {
      // switch (err.response?.status) {
      //           case 401:
      //             ErrorNotify(err.response.data);
      //             break;
      //           case 404:
      //             ErrorNotify(err.response.data);
      //             break;
      //            case 403:
      //             ErrorNotify("You don't have permission");
      //             break;
      //           case 500:
      //             ErrorNotify('Serverda xatolik yuz berdi');
      //             break;
      //           default:
      //             // Handle other status codes as needed
      //             break;
      //         }
    });
};





