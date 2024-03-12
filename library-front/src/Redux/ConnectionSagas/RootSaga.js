import {all,fork} from "redux-saga/effects"
import { categorySaga } from "../../Admin/AdminCategory/Redux/Sagas/CategorySaga";
import { BooksSaga } from "../../Admin/Adabiyotlar/Redux/Sagas/BooksSaga";


export function* RootSaga(){    
    try{
        yield all([
            fork(categorySaga),
            fork(BooksSaga),
        ])
    }catch(error) {
        // console.log(error);
    }
}