<template>
  <div>
    <div id="main">
        <mavon-editor v-model="value"/>
    </div>
  </div>
</template>
<script>
  // Local Registration
 // import marked from 'marked'
  import {
    mavonEditor
  } from 'mavon-editor'
  //import 'mavon-editor/dist/css/index.css'
  export default {
    name: 'editor',
    components: {
      mavonEditor,
     // marked
      // or 'mavon-editor': mavonEditor
    },
    data() {
      return {
        input: "#hello",

      };
    },
    computed: {
      compiledMarkdown: function() {
        return marked(this.input, {
          sanitize: true
        })
      }
    },
    methods: {
      update: function(e) {
        this.input = e.target.value
      }
    }

  }
</script>
<style>
  #editor {
    margin: auto;
    width: 80%;
    height: 580px;
  }
  
  html,
  body,
  #editor {
    margin: 0;
    height: 100%;
    font-family: 'Helvetica Neue', Arial, sans-serif;
    color: #333;
  }
  
  textarea,
  #editor div {
    display: inline-block;
    width: 49%;
    height: 100%;
    vertical-align: top;
    box-sizing: border-box;
    padding: 0 20px;
  }
  
  textarea {
    border: none;
    border-right: 1px solid #ccc;
    resize: none;
    outline: none;
    background-color: #f6f6f6;
    font-size: 14px;
    font-family: 'Monaco', courier, monospace;
    padding: 20px;
  }
  
  code {
    color: #f66;
  }

  @import "https://cdn.bootcss.com/github-markdown-css/2.10.0/github-markdown.min.css"
</style>