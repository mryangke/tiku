<template slot-scope="scope">
  <el-container>
    <el-main>
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleForm)">保存</el-button>
          <el-button @click="goback()">返回</el-button>
        </el-form-item>
        <el-form-item label="题目" prop="ti">
          <el-input type="textarea" :rows="5" v-model="ruleForm.problem"></el-input>
        </el-form-item>
        <el-form-item label="答案" prop="answer">
          <el-input type="textarea" :rows="5" v-model="ruleForm.answer"></el-input>
        </el-form-item>
        <el-form-item label="多图片" prop="pics">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt />
          </el-dialog>
        </el-form-item>
        <el-form-item label="音频" prop="sound">
          <div style="float: left; margin-right: 10px">
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :beforeUpload="beforeUpload"
              :file-list="fileList"
            >
              <el-button size="small" type="primary" style="margin-top: 10px">点击上传</el-button>
            </el-upload>
          </div>
          <div style="float: left; margin-top: 5px">
            <span style="margin-right: 10px; ">123.mp3</span>
            <el-button type="primary" icon="el-icon-caret-right" size="mini" circle></el-button>
          </div>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-select
            v-model="value"
            multiple
            filterable
            allow-create
            default-first-option
            size="medium"
            placeholder="请选择题目标签"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="额外信息">
          <el-input-number
            v-model="OptionNum"
            @change="OptionHandleChange"
            :min="0"
            :max="10"
            label="描述文字"
          ></el-input-number>
          <div v-for="index in OptionNum" :key="index">
            <el-form :model="form" label-width="0px" @input="addOption">
              <el-row>
                <el-col :span="6">
                  <el-form-item label>
                    <label>key值：</label>
                    <el-input v-model="form.option[index]" placeholder="请输入选项"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label>
                    <label>value值：</label>
                    <el-input v-model="form.text[index]" placeholder="请输入内容"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import ProblemFullData from "../data/model/ProblemFullData";
import { changeProblem } from "../api/Problem";
import { getTagsList } from "../api/Tag";

function back() {
  this.$router.push({ path: "/TikuTable" });
}

//获取标签列表
function getTags() {
  var _this = this;
  let callback = (pd, size) => {
    this.$store.commit("setNewCommits", pd);
    pd.filter(v => {
      let ress = {
        value: v.value,
        label: v.value
      };
      this.options.push(ress);
    });
  };
  getTagsList(0, callback, 0);
}

function goback() {
  this.$store.commit("setUseLastPage", true); //使用保存的页数
  this.$router.go(-1);
}

//保存修改
function submitForm(formName) {
  let _this = this;
  const problemIndex = this.$route.query.modifyIndex;
  let selectedProblem = this.$store.state.allProblem[problemIndex];
  selectedProblem.answer.answerText = formName.answer; //重新赋修改后的值
  selectedProblem.problem.problemText = formName.problem;

  selectedProblem.tags = [];
  this.value.forEach(v => {
    selectedProblem.tags.push({
      value: v
    });
  });
  selectedProblem.extData = {};
  var me = this;
  Object.keys(this.form.text).forEach(function(key) {
    //重新赋额外信息值
    let keyname = me.form.option[key];
    let value = me.form.text[key];
    selectedProblem.extData[keyname] = value;
  });
  changeProblem(selectedProblem, b => {
    if (b.code === "ok") {
      this.$message({ type: "success", message: "修改成功!" });
      // setTimeout(function() {
        this.$router.replace({ path: "/VerifyTable" });
      // }, 500);
      // todo 返回上一页
    } else {
      this.$message({ type: "error", message: "修改失败!\n" + b.data });
    }
  });
}

function resetForm(formName) {
  this.$refs[formName].resetFields();
}

function handleRemove(file) {}

function handlePreview(file) {}

function handleExceed(files, fileList) {
  this.$message.warning(
    `当前限制选择 1 个文件，本次选择了 ${
      files.length
    } 个文件，共选择了 ${files.length + fileList.length} 个文件`
  );
}

function beforeRemove(file, fileList) {
  return this.$confirm(`确定移除 ${file.name}？`);
}

function beforeUpload(file) {
  var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
  const extension = testmsg === "mp3";
  if (!extension) {
    this.$message.warning(`上传文件只能是mp3格式！`);
  }
  return extension;
}

function getParams() {
  // 取到路由带过来的参数
  const problemIndex = this.$route.query.modifyIndex;

  let selectedProblem = this.$store.state.allProblem[problemIndex];

  // 将问题和答案放在当前组件的数据内
  this.ruleForm.problem = selectedProblem.problem.problemText;
  this.ruleForm.answer = selectedProblem.answer.answerText;
  for (let i = 0; i < selectedProblem.tags.length; i++) {
    this.value.push(selectedProblem.tags[i].value);
  }
  let j = 1;
  for (let data in selectedProblem.extData) {
    this.OptionNum++;
    this.form.text[j++] = selectedProblem.extData[data];
  }
}

export default {
  name: "ModifyProblem",
  data() {
    return {
      OptionNum: 0,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      ruleForm: {
        problem: "1+1",
        answer: "2",
        pics: "",
        sound: "",
        tags: ""
      },
      form: {
        option: ["", "choice_A", "choice_B", "choice_C", "choice_D"],
        text: {}
      },
      rules: {
        problem: [{ required: true, message: "请输入题目", trigger: "blur" }],
        answer: [{ required: true, message: "请输入题目", trigger: "blur" }]
      },
      options: [],
      value: []
    };
  },
  // 页面渲染前执行取路由带过来的参数
  created() {
    this.getParams();
  },
  mounted: function() {
    this.getTags();
  },
  methods: {
    back,
    getTags,
    goback,
    submitForm,
    resetForm,
    handleRemove,
    handlePreview,
    handleExceed,
    beforeRemove,
    beforeUpload,
    getParams
  },
  //监听路由变化
  watch: {
    $route: "getParams"
  }
};
</script>

<style scoped>
</style>
