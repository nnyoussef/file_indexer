<script lang="ts">
import axios from "axios-observable";
import {ref} from "vue";

function flattenObject(ob) {
  const indexToPropertiesMap = {};

  Object.entries(ob).forEach(e => {
    const indexName = e[0];
    const properties = e[1].mappings.properties;
    const flattenedProperties = [];
    f(flattenedProperties, '', properties);
    indexToPropertiesMap[indexName] = flattenedProperties;
  })

  function f(results, indexName, mappings) {
    Object.entries(mappings).forEach(e => {
      if (e[1].type === undefined) {
        f(results, `${indexName}${e[0]}.`, e[1].properties)
      } else {
        results.push(indexName + e[0])
      }
    });
  }

  return indexToPropertiesMap;
}

export default {
  name: "UploadFile",
  mounted() {
    axios.get('http://localhost:8080/index_management/get_all_indices').subscribe(response => {
      this.indexes = flattenObject(response.data);
      this.indexesList = Object.keys(this.indexes);
    })
  },
  data: () => ({
    indexes: ref(),
    indexesList: ref([]),
    mapping: ref([]),
    inputIndex: ref(''),
    inputIndices: {},
    inputFiles: ref([])
  }),
  methods: {
    onIndexChanged(index) {
      this.inputIndices = {
        created_at: Date.now()
      };
      this.mapping = this.indexes[index.target.value].filter(s => s !== 'created_at');
    },
    fileSelectionChange(event) {
      this.inputFiles = event.files;
    },
    submit() {
      this.inputIndices['created_at'] = Date.now();
      const formData = new FormData();
      formData.append('index', this.inputIndex);
      formData.append('indices', JSON.stringify(this.inputIndices));
      Object.values(this.inputFiles).forEach(f => {
        formData.append('files', f, f.name);
      });
      axios.post('http://localhost:8080/index_management/index/files', formData).subscribe();
    }
  }
}
</script>

<template>

  <div
      style="display: grid; grid-template-areas:'control control control' 'col1 col2 col3' ;width: fit-content;column-gap: 50px;row-gap: 25px">

    <div style="grid-area: control">
      <div @click="submit()"
           style="margin-top: 18px;background: seagreen;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle;font-size: 14pt"
                class="material-symbols-outlined unselectable">
            send
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          Index File</p>
      </div>
    </div>

    <div style="grid-area: col1" class="formComponent">
      <div class="formTitle">
        <p>Select Source Index</p>
      </div>
      <div class="formBody">
        <table>
          <tr>
            <td>
              <select v-model.lazy="inputIndex" @change="onIndexChanged($event)"
                      style="display: inline-block;vertical-align: top;padding-right: 18px;border-radius: 6px;">
                <option value="" selected disabled>Choose the source index</option>
                <option v-for="(item, index) in this.indexesList" :id="index" :value="item">{{ item }}</option>
              </select>
            </td>
            <td>
              <button popovertarget="indexDoc"
                      v-if="inputIndex!==''"
                      class="material-symbols-outlined button unselectable"
                      style="color: midnightblue;background: none;border: none">info
              </button>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div style="grid-area: col2" class="formComponent">
      <div class="formTitle">
        <p>Setup Mappings</p>
      </div>
      <div class="formBody" style="max-height: 405px;overflow: auto">
        <table>
          <tr v-for="(item,index) in this.mapping">
            <td style="font-family: monospace;font-size: 10pt">
              {{ item }}
            </td>
            <td>
              <input v-model.lazy="inputIndices[item]"
                     style="width: 250px;display: inline-block;vertical-align: middle" type="text">
            </td>
          </tr>
        </table>

      </div>
    </div>

    <div style="grid-area: col3" class="formComponent">
      <div class="formTitle">
        <p>Select File To Index</p>
      </div>
      <div class="formBody">
        <div style="display: inline-block;vertical-align: top">
          <input multiple @change="fileSelectionChange($event.target)" type="file" style="width: auto;height: auto">
        </div>
      </div>
    </div>

  </div>
  <iframe popover
          id="indexDoc"
          :src="`http://localhost:8080/file_repo/${inputIndex}_description.html`"
          style="max-width: 90vw;height: 90vh;max-height: 90vh;overflow: auto;width: 90vw;"/>
</template>

<style scoped>

option,
select,
label {
  font-family: monospace;
  font-style: italic;
  font-size: 11pt;
  padding: 8px;
}

input {
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
  font-weight: bold;
}

input::placeholder {
  font-size: 10pt;
  font-style: italic;
}

.formComponent {
  display: flex;
  flex-direction: column;
  width: fit-content;
  height: fit-content;
  border: 1px solid lightgray;
  padding-bottom: 18px;
}

div.formTitle {
  background: whitesmoke;
  border-bottom: 1px solid lightgray;
  padding-right: 18px;
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

div.formBody {
  padding-right: 18px;
  padding-top: 18px;
}
</style>