import {all,fork} from "redux-saga/effects"


export function* RootSaga(){
    try{
        yield all([
            // fork(tableSaga),
        ])
    }catch(error) {
        console.log(err);
    }
}