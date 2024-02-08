<script lang="ts">

import {reactive, ref} from "vue";
import axios from "axios";

export default {
  name: "CreateIndex",
  data: () => ({
    indexName: ref(''),
    indexDescription: '',
    mapping: reactive([''])
  }),
  methods: {
    createIndex() {
      axios.post('http://localhost:8080/index_management/create/index', {
        indexName: this.indexName,
        mappings: this.mapping
      });
    },
    addLabel() {
      this.mapping.push('');
    },
    deleteMapping(index: number) {
      if (this.mapping.length > 1)
        this.mapping.splice(index, 1);
    },
    resetMapping() {
      this.mapping = [''];
    }
  }
}
</script>

<template>
  <div
      style="display: grid; grid-template-areas:'control control' 'col1 col2' ;width: fit-content;column-gap: 50px;row-gap: 25px">
    <div style="grid-area: col1" class="formComponent">
      <div class="formTitle">
        <p>Index Name</p>
      </div>
      <div class="formBody">
        <table>
          <tr>
            <td style="vertical-align: bottom;" class="label">Index Name</td>
            <td><input  v-model.lazy="this.indexName" class="input" type="text" min="8"
                       maxlength="255"></td>

          </tr>
          <tr style="margin-top: 24px">
            <td style="vertical-align: baseline" class="label">Index Description</td>
            <td> <textarea v-model.lazy="this.indexDescription" class="input"
                           rows="20"
                           style="margin-top: 0;height: auto"
                           maxlength="1000"/></td>
          </tr>
        </table>


      </div>
    </div>
    <div style="grid-area: col2" class="formComponent">
      <div class="formTitle">
        <p>Mappings</p>
      </div>
      <div class="formBody">
        <div
            style="margin-top: 18px;background: midnightblue;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer"
            @click="this.addLabel()">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            add_circle
            </span>
          <p class="unselectable"
             style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
            Add</p>
        </div>
        <div
            style="margin-top: 18px;background: lightseagreen;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer"
            @click="this.resetMapping()">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            restart_alt
            </span>
          <p class="unselectable"
             style="margin: 0 0 0 8px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
            Reset</p>
        </div>
        <div style="height: 400px;max-height: 400px;overflow-y: auto;">
          <div style="margin-top: 24px" v-for="(item, index) in this.mapping">
            <span style="display: inline-block; vertical-align: middle;" class="material-symbols-outlined unselectable">label</span>
            <input class="input" style="margin-top: 0;margin-left: 6px" v-model.lazy="this.mapping[index]" type="text"
                   maxlength="30" minlength="1" placeholder="Mapping key">
            <span v-if="index > 0"
                  style="display: inline-block; vertical-align: middle;margin-left: 24px;color: orangered;cursor: pointer"
                  class="material-symbols-outlined unselectable" @click="this.deleteMapping(index)">delete</span>
          </div>
        </div>
      </div>
    </div>
    <div style="grid-area: control">
      <div
          style="margin-top: 18px;background: seagreen;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer"
          @click="this.createIndex()">
          <span style="display: inline-block;vertical-align: middle;font-size: 14pt"
                class="material-symbols-outlined unselectable">
            send
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          Create Index</p>
      </div>
    </div>
  </div>

</template>

<style scoped>
label,
.label {
  font-family: monospace;
  font-style: italic;
  font-size: 11pt;
  padding: 4px;
}

.formComponent {
  display: flex;
  flex-direction: column;
  width: 450px;
  height: fit-content;
  border: 1px solid lightgray;
  padding-bottom: 18px;
}


.input {
  background-color: #FFFFFF;
  border: 1px solid #FFFFFF;
  box-shadow: 1px 3px 3px #D8D9D9 inset;
  color: midnightblue;
  font-family: monospace;
  font-size: 14pt;
  height: 22px;
  margin-left: 0;
  outline: 1px solid #D9D9D9;
  padding-left: 4px;
  padding-right: 20px;
  vertical-align: middle;
  width: 200px;
  margin-top: 12px;
  font-weight: bold;
}

.input::placeholder {
  font-size: 10pt;
  font-style: italic;
}

div.formTitle {
  background: whitesmoke;
  border-bottom: 1px solid lightgray;
}

div.formTitle > p {
  color: midnightblue;
  font-family: monospace;
  font-size: 12pt;
  font-weight: bold;
  margin-left: 18px;
}

div.formBody > * {
  margin-left: 18px;
}

.unselectable {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

</style>