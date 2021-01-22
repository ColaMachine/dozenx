<template>
  <div class="app-container">

    <div id="container" style="width: 600px; height: 400px"/>
    <el-button type="text" @click="dialogFormVisible = true">添加子功能模块</el-button>

    <el-dialog :visible.sync="dialogFormVisible" title="模块名称">
      <el-form :model="form">
        <el-form-item :label-width="formLabelWidth" label="模块名称">
          <el-input v-model="form.moduleName" autocomplete="off"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="append()">确 定</el-button>
      </div>
    </el-dialog>

    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">

          <div class="custom-tree-container">
            <div class="block">
              <p>功能模块维护</p>
              <el-tree
                :data="treeData"
                :props="defaultProps"

                :expand-on-click-node="false"
                :render-content="renderContent"

                :load="loadData"
                :allow-drop="allowDrop"
                :allow-drag="allowDrag"
                node-key="id"
                draggable
                lazy
                @node-click="nodeClick"

                @node-drag-start="handleDragStart"
                @node-drag-enter="handleDragEnter"
                @node-drag-leave="handleDragLeave"
                @node-drag-over="handleDragOver"
                @node-drag-end="handleDragEnd"
                @node-drop="handleDrop"

              />
            </div>

          </div>
        </div>
      </el-col>
      <el-col :span="18">

        <editForm ref="editForm"/>
        <div class="grid-content bg-purple">

          <!-- 查询和其他操作 -->
          <div class="filter-container">
            <el-input
              v-model="listQuery.interfaceNameLike"
              clearable
              class="filter-item"
              style="width: 200px;"
              placeholder="请输入接口名称"/>
            <el-input
              v-model="listQuery.moduleNameLike"
              clearable
              class="filter-item"
              style="width: 200px;"
              placeholder="所属模块"/>
            <el-input
              v-model="listQuery.urlLike"
              clearable
              class="filter-item"
              style="width: 200px;"
              placeholder="url"/>
            <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
            <el-button
              :loading="downloadLoading"
              class="filter-item"
              type="primary"
              icon="el-icon-download"
              @click="handleDownload">导出
            </el-button>
            <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
          </div>

          <!-- 查询结果 -->
          <el-table
            v-loading="listLoading"
            :data="list"
            element-loading-text="正在查询中。。。"
            border
            fit
            highlight-current-row>
            <el-table-column align="center" width="100px" label="ID" prop="id" sortable/>

            <el-table-column align="center" label="接口名称" prop="interfaceName"/>

            <el-table-column align="center" label="url" prop="url"/>
            <el-table-column align="center" label="httpType" prop="httpType"/>

            <el-table-column align="center" label="所属模块" prop="moduleName"/>

            <el-table-column align="center" label="作者" prop="author"/>
            <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
              <template slot-scope="scope">

                <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
                <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>

            <el-table-column align="center" label="状态" prop="status"/>
            <el-table-column align="center" label="接口id" prop="interfaceId"/>

            <el-table-column align="center" label="版本" prop="version"/>
            <el-table-column align="center" label="contentType" prop="contentType"/>

          </el-table>

          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="listQuery.page"
            :limit.sync="listQuery.limit"
            @pagination="getList"/>

        </div>
      </el-col>

    </el-row>

  </div>
</template>

<script>
import { fetchList, deleteApi, interfaceInfoModuleTree, interfaceInfoModuleGetLeaf, interfaceInfoModuleTreeLeaf, interfaceInfoModuleAdd, interfaceInfoModuleDelete, interfaceInfoModuleUpdate, updateInterfaceInfoModule } from '@/api/api'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { x6 } from '@antv/x6'
import editForm from './edit' // Secondary package based on el-pagination
const id = 1000
export default {
  name: 'User',
  components: { Pagination, editForm },
  data() {
    return {
      list: null,
      form: {},
      total: 0, dialogFormVisible: false,
      listLoading: true,
      nowParentData: null,
      listQuery: {
        page: 1,
        limit: 20,
        username: undefined,
        mobile: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      downloadLoading: false,
      genderDic: ['未知', '男', '女'],
      levelDic: ['普通用户', 'VIP用户', '高级VIP用户'],
      statusDic: ['可用', '禁用', '注销'],

      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'name',
        icon: 'icon'
      }
    }
  },
  created() {
    this.getList()
    this.getTreeData()

    const container = document.getElementById('container')
    const graph = x6.render(container, {
      nodes: [
        {
          id: 'node-0',
          x: 60,
          y: 60,
          width: 80,
          height: 30,
          label: 'Hello'
        },
        {
          id: 'node-1',
          x: 240,
          y: 240,
          width: 80,
          height: 30,
          label: 'World'
        }
      ],
      edges: [
        {
          id: 'edge-0',
          source: 'node-0',
          target: 'node-1',
          label: 'Edge Label'
        }
      ]
    })
  },
  methods: {

    loadData(node, resolve) {
      console.log(node)
      interfaceInfoModuleGetLeaf(node.data.id).then(response => {
        console.log(response.data.data)
        // node.children = response.data.data;
        resolve(response.data.data)
      }).catch(() => {

      })
    },
    handleDragStart(node, ev) {
      console.log('drag start', node)
    },
    handleDragEnter(draggingNode, dropNode, ev) {
      console.log('tree drag enter: ', dropNode.label)
    },
    handleDragLeave(draggingNode, dropNode, ev) {
      console.log('tree drag leave: ', dropNode.label)
    },
    handleDragOver(draggingNode, dropNode, ev) {
      console.log('tree drag over: ', dropNode.label)
    },
    handleDragEnd(draggingNode, dropNode, dropType, ev) {
      console.log('tree drag over: ', dropNode.label)
      //   console.log('tree drag end: ', dropNode.data.name ,draggingNode.data.name);
    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      // console.log('tree drop: ', dropNode.data.name ,draggingNode.data.name);
      console.log('tree drag over: ', dropNode.label)
      var data = { id: draggingNode.data.id, moduleId: dropNode.data.id, moduleName: dropNode.data.name }
      updateInterfaceInfoModule(data).then(response => {
        if (response.data.r == 0) {
          this.$notify.success({
            title: '成功',
            message: '更改模块成功'
          })
        } else {
          this.$notify.error({
            title: '失败',
            message: response.data.msg
          })
        }
      })
    },
    allowDrop(draggingNode, dropNode, type) {
      if ((dropNode.data.type === 0 && draggingNode.data.type === 1) || (dropNode.data.type === 0 && draggingNode.data.type === 0)) {
        console.log(' allow', dropNode.data.name, draggingNode.data.name)
        return true
      } else { // console.log("not allow");
        console.log(' not allow', dropNode.data.name, draggingNode.data.name)
        return false
      }
    },
    allowDrag(draggingNode) {
      return true
      // return draggingNode.data.label.indexOf('三级 3-2-2') === -1;
    },
    nodeClick(data) {
      if (data.type === 0) {

      // 只有点击箭头才展开
        /** interfaceInfoModuleGetLeaf(data.id).then(response => {
      console.log(response.data.data);
          data.children = response.data.data;

        }).catch(() => {

        })
        this.$set(this.listQuery,"interfaceNameLike","");
        this.$set(this.listQuery,"moduleNameLike",data.name);**/

      } else if (data.type === 1) {
        this.$set(this.listQuery, 'interfaceNameLike', data.name)
        this.$set(this.listQuery, 'moduleNameLike', '')
      }
      // this.getList();
      console.log(data)
      this.$refs.editForm.init2(data.id - 1000000)
    },
    getTreeData() {
      if (this.treeData.length > 0) { return }
      // interfaceInfoModuleTreeLeaf().then(response => {
      interfaceInfoModuleTree().then(response => {
        console.log(response.data.data)
        this.treeData = response.data.data.children
        for (var i in response.data.data.children) {
          //  console.log(i);
          // i.icon= 'el-icon-info';
        }
      }).catch(() => {

      })
    },
    getList() {
      this.listQuery.curPage = this.listQuery.page
      this.listQuery.pageSize = this.listQuery.limit
      this.listLoading = true

      fetchList(this.listQuery).then(response => {
        this.list = response.data.data
        this.total = response.data.page.totalCount
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },

    handleUpdate(row) {
      this.$refs.editForm.init2(row.id)
      // this.$router.push({ path: '/api/edit', query: { id: row.id }})
    },

    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['用户名', '手机号码', '性别', '生日', '状态']
        const filterVal = ['username', 'mobile', 'gender', 'birthday', 'status']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '用户信息')
        this.downloadLoading = false
      })
    },

    handleAdd() {
      this.$router.push({ path: '/api/edit', query: { id: null }})
    },

    handleDelete(row) {
      this.$confirm('此操作将永久删除该api, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteApi(row.id).then(response => {
          if (response.data.r == 0) {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            })
          } else {
            this.$notify.error({
              title: '失败',
              message: response.data.msg
            })
          }
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    showAddModuleDialogue(data) {
      this.nowParentData = data
      console.log('this.nowParentData', this.nowParentData)
      this.dialogFormVisible = true
    },

    showEditModuleDialogue(data) {
      this.nowParentData = data
      console.log('this.nowParentData', this.nowParentData)
      this.form = {}
      this.form.id = data.id
      // this.form.moduleName=data.name;

      this.$set(this.form, 'moduleName', data.name)
      this.form.pid = data.pid

      this.dialogFormVisible = true
    },
    append() {
    // ajax 添加数据模块
      if (this.form.id) {
        var data = { 'id': this.form.id, 'moduleName': this.form.moduleName, 'pid': this.form.pid }
        interfaceInfoModuleUpdate(data).then(response => {
          if (response.data.r == 0) {
            this.$notify.success({
              title: '成功',
              message: '修改成功'
            })
            this.dialogFormVisible = false

            this.nowParentData.name = this.form.moduleName
          } else {
            this.$notify.error({
              title: '失败',
              message: response.data.msg
            })
          }
          // this.getList();
        })
      } else {
        var data = { 'moduleName': this.form.moduleName, 'pid': this.nowParentData ? this.nowParentData.id : 0 }
        interfaceInfoModuleAdd(data).then(response => {
          if (response.data.r == 0) {
            this.$notify.success({
              title: '成功',
              message: '添加成功'
            })

            if (this.nowParentData) {
              if (this.nowParentData && !this.nowParentData.children) {
                this.$set(this.nowParentData, 'children', [])
              }
              this.nowParentData.children.push(newChild)
            }

            const newChild = { id: response.data.data.id, name: response.data.data.moduleName, children: [], pid: response.data.data.pid, type: 0 }
            console.log(newChild)

            this.dialogFormVisible = false
          } else {
            this.$notify.error({
              title: '失败',
              message: response.data.msg
            })
          }
          // this.getList();
        })
      }

      /* const newChild = { id: id++, label: 'testtest', children: [] };
      if (!data.children) {
        this.$set(data, 'children', []);
      }
      data.children.push(newChild);*/
    },

    remove(node, data) {
      this.$confirm('此操作将永久删除该模块, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        interfaceInfoModuleDelete(data.id).then(response => {
          if (response.data.r == 0) {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            })

            const parent = node.parent
            const children = parent.data.children || parent.data
            const index = children.findIndex(d => d.id === data.id)
            children.splice(index, 1)
          } else {
            this.$notify.error({
              title: '失败',
              message: response.data.msg
            })
          }
          // this.getList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    renderContent(h, { node, data, store }) {
      console.log(data)
      if (data.type === 1) {
        return (

          <span class='custom-tree-node'>

            <span>   {node.label}</span>

          </span>)
      }
      return (
        <span class='custom-tree-node'>

          <span><i class='el-icon-folder'></i>{node.label}</span>
          <span>
            <el-button size='mini' type='text' on-click={ () => this.showAddModuleDialogue(data) }>添加</el-button>
            <el-button size='mini' type='text' on-click={ () => this.showEditModuleDialogue(data) }>修改</el-button>
            <el-button size='mini' type='text' on-click={ () => this.remove(node, data) }>删除</el-button>
          </span>
        </span>)
    }

  }
}

</script>
