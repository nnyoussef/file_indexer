<script lang="ts">
import axios from "axios-observable";

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
  name: "Search",
  data: () => ({
    mapping: [],
    indexes: [],
    indexeIndicesMapping: {},
    searchCriteria: {},
    indexName: '',
    files: ['No Content'],
    dateFrom: 0,
    dateTo: Date.now()
  }),
  mounted() {
    axios.get('http://localhost:8080/index_management/get_all_indices').subscribe(e => {
      requestIdleCallback(() => {
        this.indexeIndicesMapping = flattenObject(e.data);
        this.indexes = Object.keys(this.indexeIndicesMapping)
      })
    })
  },
  methods: {
    onIndexSelected(event) {
      this.searchCriteria = {};
      this.mapping = this.indexeIndicesMapping[event.value].filter(e => e !== 'created_at');
      this.indexName = event.value;
    },
    submit() {
      axios.post('http://localhost:8080/index_management/search_by_index_indices', {
        indexName: this.indexName,
        dateFrom: Date.parse(this.dateFrom),
        dateTo: Date.parse(this.dateTo),
        data: this.searchCriteria
      }).subscribe((s) => {
        if (s.data.length === 0)
          this.files = ['No Content'];
        else
          this.files = s.data;
      })
    },
    getDownloadLinkFromFileName(fileName) {
      return `http://localhost:8080/index_management/download/${this.indexName}/${fileName}`;
    }
  }
}
</script>

<template>

  <div style="height:100%;width: fit-content;display: grid;grid-template-rows: 50px auto ;grid-column-gap: 24px">

    <div style="grid-row: 1/2">
      <div @click="submit()"
           style="margin-top: 0;background: lightseagreen;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle;font-size: 14pt"
                class="material-symbols-outlined unselectable">
            search
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          Search</p>
      </div>
    </div>

    <div style="grid-row: 2/3" class="formComponent">
      <div class="formTitle">
        <p>Search Criteria</p>
      </div>
      <div class="formBody" style="max-height: 405px;overflow: auto">
        <table>
          <tr>
            <td style="padding-right: 12px" class="label">Date Range:</td>
            <td style="font-family: monospace;font-size: 10pt">
              <input style="width: 200px;display: inline-block;vertical-align: middle" v-model.lazy="dateFrom"
                     type="datetime-local">
            </td>
            <td style="padding-left: 4px; padding-right: 4px">-</td>
            <td>
              <input style="width: 200px;display: inline-block;vertical-align: middle" v-model.lazy="dateTo"
                     type="datetime-local">
            </td>
          </tr>
        </table>
      </div>
      <div class="formBody" style="max-height: 405px;overflow: auto">
        <table>
          <tr>
            <td class="label">Choose Index:</td>
            <td><select
                @change="onIndexSelected($event.target)"
                style="display: inline-block;vertical-align: top;padding-right: 18px;border-radius: 6px;">
              <option disabled selected>Select One</option>
              <option v-for="(item, index) in this.indexes" :id="index" :value="item">{{ item }}</option>
            </select></td>
            <td>
              <button popovertarget="indexDoc"
                      v-if="indexName!==''"
                      class="material-symbols-outlined button unselectable"
                      style="color: midnightblue;background: none;border: none">info
              </button>
            </td>
          </tr>
        </table>
      </div>
      <div class="formBody">
        <div>
          <table>
            <tr v-for="(item,index) in mapping">
              <td class="label">{{ item }}</td>
              <td><input v-model="searchCriteria[item]"
                         style="width: 400px;display: inline-block;vertical-align: middle" type="text"></td>
            </tr>
          </table>
        </div>
      </div>
    </div>

    <div style="grid-row: 2/3" class="formComponent">
      <div class="formTitle">
        <p>Results</p>
      </div>
      <div class="formBody" style=";overflow: hidden;flex-grow: 1;min-height: 0">
        <div style="max-height: 100%;overflow: auto;">
          <table>
            <tr v-for="(item,index) in files">
              <td style="padding-right: 12px" v-if="item !=='No Content'">
                <a style="color: lightseagreen;"
                   :href="getDownloadLinkFromFileName(item)"
                   class="material-symbols-outlined unselectable button">download</a>
              </td>
              <td style="width: 500px;font-style: italic;font-family: monospace;font-size: 12pt">
                {{ item }}
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>


  </div>
  <iframe popover id="indexDoc" :src="`http://localhost:8080/file_repo/${indexName}_description.html`"
          style="max-width: 90vw;height: 90vh;max-height: 90vh;overflow: auto;width: 90vw;"/>
</template>


<style scoped>
option,
select,
label,
.label {
  font-family: monospace;
  font-style: italic;
  font-size: 11pt;
  padding: 4px;
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
  border: 1px solid lightgray;
  padding-bottom: 12px;
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