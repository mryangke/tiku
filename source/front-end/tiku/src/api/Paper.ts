import axios from 'axios'
import PaperFullData from '@/data/model/PaperFullData';
import BasicResponse from '@/data/model/BasicResponse';
import TagInfo from '@/data/model/Tag';
/**
 * 拿到试卷列表方法
 * @param callback 回调函数
 */
function getPapers(pageNumber: number, callback: (p: PaperFullData[], listSize: number) => void, isDeep?: number) {
    axios
        .get("/api/paper/list?pageNumber=" + pageNumber + "&pageSize=10&isDeep=" + isDeep)//服务器地址
        .then(res => {
            //console.log(res)
            let lists: PaperFullData[] = res.data.data.paperFullDataList;
            let size : number = res.data.data.pagination.total;
            callback(lists ,size)
        })
}

/**
 * 增加试卷方法
 * @param paper 需要增加的试卷 ( PaperFullData 类型)
 * @param callback
 */
function addPaper(paper: PaperFullData, callback: (b: BasicResponse) => void) {

    axios
        .post("/api/paper/add", paper)
        .then(res => {
            //是否添加试卷
            let response: BasicResponse = res.data;
            callback(response)
        })

}

/**
 * 删除试卷
 * @param paperId 试卷id
 * @param callback 回调函数
 */
function delPaper(idList: number[], callback: (b: BasicResponse) => void) {
    axios
        .post("/api/paper/delete", idList)
        .then(res => {
            let response: BasicResponse = res.data;
            callback(response)
        })
}

/**
 * 查找方法 ( 通过标题 ) (模糊查询) ( 后台方法未完成 )
 * @param paperTitle 试卷标题模糊查询
 * @param callback 回调函数
 */
function findPaperByTitle(paperTitle: string, callback: (p: PaperFullData[]) => void) {
    axios
        .post("/api/paper/find/title", paperTitle)
        .then(res => {
            let response: PaperFullData[] = res.data;
            callback(response)
        })
}

/**
 * 查找试卷方法 ( 通过标签 )
 * @param tags 试卷标签查询
 * @param callback 回调函数
 */
function findPaperBytags(tags: TagInfo[], callback: (p: PaperFullData[]) => void) {
    axios
        .post("/api/paper/find/tag", tags)
        .then(res => {
            let response: PaperFullData[] = res.data;
            callback(response)
        })
}

/**
 * 修改试卷方法
 * @param paperId 要修改试卷的id
 * @param callback
 */
function changePaper(paperId: number, callback: (b: BasicResponse) => void) {
    axios
        .post("/api/paper/update", paperId)
        .then(res => {
            let response: BasicResponse = res.data;

            callback(response)
        })
}


export {
    getPapers,
    addPaper,
    delPaper,
    findPaperByTitle, findPaperBytags,
    changePaper
}
