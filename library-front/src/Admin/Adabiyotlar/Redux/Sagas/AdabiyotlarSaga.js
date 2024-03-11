import { select, takeEvery, put, call } from "redux-saga/effects";
import ApiRequest from "../../../../configure/ApiRequestor/ApiRequest";
import { AdabiyotlarActions } from "../Reducers/AdabiyotlarReducer";

function getAdabiyotlar(){
    
}

export function* AdabiyotlarSaga(){
    yield takeEvery("Adabiyotlar/getAdabiyotlar", getAdabiyotlar);
}