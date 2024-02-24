<script>
import axios from "axios-observable";

export default {
  name: "Overview",
  data: () => ({
    indexes: [],
    details: {},
    selectedIndex: '',
    indexDetails: [],
    selectedIndexNewName: '',
    selectedFile: '',
    metadata: {}
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
    deleteIndex() {
      axios.delete(`http://localhost:8080/index_management/${this.selectedIndex}`).subscribe(e => {
        this.indexes.splice(this.indexes.indexOf(this.selectedIndex), 1);
        this.selectedIndex = '';
        this.indexDetails = {}
        this.closeDialogBox('deleteIndexConfirmation');
      });
    },
    renameIndex() {
      axios.post('http://localhost:8080/index_management/rename/index', {
        newIndexName: this.selectedIndexNewName,
        indexName: this.selectedIndex
      }).subscribe(data => {
        this.closeDialogBox('indexRenameBox');
      })
    },
    closeDialogBox(id) {
      document.getElementById(id).close()
    },
    openDialogBox(id) {
      document.getElementById(id).showModal()
    },
    getDownloadLinkFromFileName(fileName) {
      return `http://localhost:8080/index_management/download/${this.selectedIndex}/${fileName}`;
    },
    deleteFile() {
      axios.delete(`http://localhost:8080/index_management/${this.selectedIndex}/${this.selectedFile}`).subscribe(e => {
        this.indexDetails.splice(this.indexDetails.findIndex(e => e.FILE_NAME === this.selectedFile), 1);
        this.closeDialogBox('deleteFileConfirmation');
      })
    },
    getFileMetaData() {
      this.openDialogBox('metadata');
      axios.get(`http://localhost:8080/index_management/metadata/${this.selectedIndex}/${this.selectedFile}`).subscribe(d => {
        this.metadata = d.data;
      });
    }
  }
}
</script>

<template>

  <div
      style="display: grid;grid-template-columns: 450px auto;height: 100%;overflow: hidden;column-gap: 24px;contain: strict">
    <div style="contain: strict;grid-column: 1 / 2;display: flex;height: 50px;align-items: center">
      <select @change="onIndexSelected($event.target)"
              aria-label="index-name-select"
              style="display: inline-block;vertical-align: top;padding-right: 18px;border-radius: 6px;">
        <option disabled selected value="''">Choose an Index from the List Below</option>
        <option v-for="(item,index) in indexes" :value="item">{{ item }}</option>
      </select>
      <div v-if="selectedIndex !==''" style="margin-left: 12px">
        <button popovertarget="indexDoc"
                class="material-symbols-outlined button unselectable"
                style="color: midnightblue;background: none;border: none">info
        </button>
        <button @click="openDialogBox('indexRenameBox')"
                class="material-symbols-outlined button unselectable"
                style="color: lightseagreen;background: none;border: none">edit
        </button>
        <button
            @click="openDialogBox('deleteIndexConfirmation')"
            class="material-symbols-outlined button unselectable"
            style="color: orangered;background: none;border: none">delete
        </button>
      </div>
    </div>
    <div
        style="contain: strict;grid-column: 2 / 3;;max-height: 100%;overflow: auto;border: 2px solid lightseagreen;border-radius: 5px;">
      <table style="border-collapse: collapse;margin-top: 24px;margin-left: 12px">
        <tr>
          <td style="border-bottom: 1px solid lightgray;text-align: center" class="label">File Name</td>
          <td style="border-bottom: 1px solid lightgray;text-align: center" class="label">Created At</td>
          <td style="border-bottom: 1px solid lightgray;text-align: center" class="label">Modified At</td>
          <td colspan="2" style="border-bottom: 1px solid lightgray;text-align: center" class="label"></td>
        </tr>
        <tr class="selectable" v-for="(item,index) in indexDetails">
          <td class="label" style="padding: 12px">{{ item.FILE_NAME }}</td>
          <td class="label" style="padding: 12px">{{ item.CREATED }}</td>
          <td class="label" style="padding: 12px">{{ item.MODIFIED }}</td>
          <td @click="selectedFile = item.FILE_NAME;getFileMetaData()" class="button material-symbols-outlined"
              style="padding:12px 0 12px 12px ;color: midnightblue">list
          </td>
          <td class="button " style="padding:12px 0 12px 0 ;color: lightseagreen">
            <a style="color: lightseagreen;"
               id="download"
               :href="getDownloadLinkFromFileName(item.FILE_NAME)"
               class="material-symbols-outlined unselectable button">download</a>
          </td>
          <td @click="selectedFile = item.FILE_NAME; openDialogBox('deleteFileConfirmation')"
              class="button material-symbols-outlined" style="padding:12px 0 12px 0 ;color: orangered">delete
          </td>
        </tr>
      </table>
    </div>
    <dialog id="indexRenameBox"
            style="border: 2px solid lightseagreen;padding: 12px;border-radius: 5px">
      <section>
        <p style="margin-top: 0;font-size: 16pt" class="label">Rename Index </p>
        <input class="input" style="margin-top: 0;;display: inline-block;vertical-align: middle"
               type="text"
               v-model.lazy="selectedIndexNewName"
               maxlength="30" minlength="1" placeholder="New name">
        <button
            class="material-symbols-outlined button unselectable"
            @click="renameIndex()"
            style="color: lightseagreen;background: none;border: none;display: inline-block;vertical-align: middle;margin-left: 12px">
          send
        </button>
      </section>
      <div @click="closeDialogBox('indexRenameBox')"
           style="width:224px;margin-top: 24px;background: midnightblue;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            close
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          Cancel</p>
      </div>
    </dialog>
    <dialog id="deleteIndexConfirmation"
            style="border: 2px solid lightseagreen;height: 100px;padding: 12px;border-radius: 5px">
      <p style="font-size: 16pt;margin-top: 0" class="label">Are you sure you want to delete? </p>
      <div @click="deleteIndex()"
           style="width:43%;margin-top: 9px;background: limegreen;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            done
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          Yes</p>
      </div>
      <div @click="closeDialogBox('deleteIndexConfirmation')"
           style="width:43%;margin-left:12px;margin-top: 9px;background: orangered;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            close
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          No</p>
      </div>
    </dialog>
    <dialog id="deleteFileConfirmation"
            style="border: 2px solid lightseagreen;height: 100px;padding: 12px;border-radius: 5px">
      <p style="font-size: 16pt;margin-top: 0" class="label">Are you sure you want to delete? </p>
      <div @click="deleteFile()"
           style="width:43%;margin-top: 9px;background: limegreen;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            done
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          Yes</p>
      </div>
      <div @click="closeDialogBox('deleteFileConfirmation')"
           style="width:43%;margin-left:12px;margin-top: 9px;background: orangered;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            close
            </span>
        <p class="unselectable"
           style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
          No</p>
      </div>
    </dialog>
    <dialog id="metadata"
            style="border: 2px solid lightseagreen;padding: 12px;border-radius: 5px">
      <p style="font-size: 16pt;margin-top: 0" class="label">Meta Data for {{ selectedIndex }}/{{ selectedFile }}</p>
      <section style="max-height: 400px;overflow: auto;">
        <table>
          <tr v-for="(data,index) in Object.entries(metadata)">
            <td><p class="label">{{ data[0] }}</p></td>
            <td><input type="text" disabled :value="data[1]"></td>
          </tr>
        </table>
      </section>
      <section>
        <div @click="closeDialogBox('metadata')"
             style="width:224px;margin-top: 24px;background: midnightblue;border: none;display: inline-block;vertical-align: middle;color: white;padding: 4px;border-radius: 3px;cursor: pointer">
          <span style="display: inline-block;vertical-align: middle" class="material-symbols-outlined unselectable">
            close
            </span>
          <p class="unselectable"
             style="margin: 0 0 0 12px;display: inline-block;vertical-align: middle;font-family: monospace;font-size: 12pt">
            Cancel</p>
        </div>
      </section>
    </dialog>
  </div>
  <iframe id="indexDoc"
          popover
          :src="`http://localhost:8080/file_repo/${selectedIndex}_description.html`"
          style="max-width: 90vw;height: 90vh;max-height: 90vh;overflow: auto;width: 90vw;"/>

</template>

<style scoped>
tr.selectable:hover {
  background: aliceblue;
  cursor: pointer;
}

dialog::backdrop {
  backdrop-filter: blur(1px);
}

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