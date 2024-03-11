import {all,fork} from "redux-saga/effects"
import { categorySaga } from "../../Admin/AdminCategory/Redux/Sagas/CategorySaga";
import { AdabiyotlarSaga } from "../../Admin/Adabiyotlar/Redux/Sagas/AdabiyotlarSaga";


export function* RootSaga(){    
    try{
        yield all([
            fork(categorySaga),
            fork(AdabiyotlarSaga),
        ])
    }catch(error) {
        // console.log(error);
    }
}