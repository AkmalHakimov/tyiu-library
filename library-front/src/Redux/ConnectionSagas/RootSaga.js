import {all,fork} from "redux-saga/effects"
import { categorySaga } from "../../Admin/AdminCategory/Redux/Sagas/CategorySaga";


export function* RootSaga(){    
    try{
        yield all([
            fork(categorySaga),
        ])
    }catch(error) {
        console.log(error);
    }
}