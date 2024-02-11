<script>
import axios from "axios-observable";
import Button from "@/02-fragments/Button.vue";

export default {
  name: "Overview",
  components: {Button},
  data: () => ({
    indexes: [],
    details: {},
    selectedIndex: '',
    indexDetails: []
  }),
  mounted() {
    axios.get('http://localhost:8080/index_management/get/details/all').subscribe(d => {
      this.details = d.data;
      this.indexes = Object.keys(d.data);

    });
  },
  methods: {
    onIndexSelected(event) {
      this.selectedIndex = event.value;
      this.indexDetails = this.details[event.value]
    },
  }
}
</script>

<template>
  <div style="display: grid;grid-template-columns: 420px auto;height: 100%;overflow: hidden;column-gap: 24px;">
    <div style=" grid-column: 1 / 2;display: flex;height: fit-content">
      <select @change="onIndexSelected($event.target)"
              style="display: inline-block;vertical-align: top;padding-right: 18px;border-radius: 6px;">
        <option disabled selected>Choose an Index from the List Below</option>
        <option v-for="(item,index) in indexes" :value="item">{{ item }}</option>
      </select>
      <div v-if="selectedIndex !==''" style="margin-left: 12px">
        <button popovertarget="indexDoc"
                class="material-symbols-outlined button unselectable"
                style="color: midnightblue;background: none;border: none">info
        </button>
        <button popovertarget="indexDoc"
                class="material-symbols-outlined button unselectable"
                style="color: lightseagreen;background: none;border: none">edit
        </button>
        <button popovertarget="indexDoc"
                class="material-symbols-outlined button unselectable"
                style="color: orangered;background: none;border: none">delete
        </button>
      </div>
    </div>
    <div
        style=" grid-column: 2 / 3;;max-height: 100%;overflow: auto;border: 2px solid lightseagreen;border-radius: 5px;">
      <table style="border-collapse: collapse;margin-top: 24px;margin-left: 12px">
        <tr>
          <td style="border-bottom: 1px solid lightgray;text-align: center" class="label">File Name</td>
          <td style="border-bottom: 1px solid lightgray;text-align: center" class="label">Created At</td>
          <td style="border-bottom: 1px solid lightgray;text-align: center" class="label">Modified At</td>
          <td colspan="2" style="border-bottom: 1px solid lightgray;text-align: center" class="label"></td>
        </tr>
        <tr v-for="(item,index) in indexDetails">
          <td class="label" style="padding: 12px">{{ item.FILE_NAME }}</td>
          <td class="label" style="padding: 12px">{{ item.CREATED }}</td>
          <td class="label" style="padding: 12px">{{ item.MODIFIED }}</td>
          <td class="button material-symbols-outlined" style="padding:12px 0 12px 12px ;color: midnightblue">info</td>
          <td class="button material-symbols-outlined" style="padding:12px 0 12px 0 ;color: orangered">delete</td>
        </tr>
      </table>
    </div>
  </div>
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

button {
  background: none;
  border: none;
  padding: 0;
}
</style>