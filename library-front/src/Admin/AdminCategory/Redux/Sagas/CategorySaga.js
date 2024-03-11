import {select, takeEvery,put,call} from "redux-saga/effects";
import ApiRequest from "../../../../configure/ApiRequestor/ApiRequest"; 
import { categoryActions } from "../Reducers/CategoryReducer";


function* getCategories(action){
    const states = yield select((state)=>state.category)
    categoryActions.setIsLoading()
    const res = yield ApiRequest(`/category?page=${states.page}&search=`+ (states.searchInp),"get")
    yield put(categoryActions.setDataSource(res.data.content))
    yield put(categoryActions.setTotalPages(res.data.totalElements))
}

function* getHandleSave(action){
    const states = yield select((state)=>state.category)
    if (states.currentItm) {
        const res = yield ApiRequest("/category/" + states.currentItm.id,"put",{
            name: states.categoryInp,
          })
         yield put(categoryActions.setCurrentItm(""));
          categoryActions.getCategories();
      } else {
        const res = yield ApiRequest("/category","post",{
            name: states.categoryInp,
          })
          yield call(getCategories())
      }
      yield put(categoryActions.setCategoryInp(""));
      categoryActions.setIsModal()
}

export function* categorySaga(){
    yield takeEvery("category/getCategories",getCategories) 
    yield takeEvery("category/handleSave",getHandleSave)  
}