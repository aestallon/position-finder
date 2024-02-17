<script setup lang="ts">
import { Position } from '@/api/pfinder';
import { positionApi } from '@/services';
import { ref } from 'vue';

const showCreateDialog = ref<boolean>(false);

const name = ref<string>('');
const location = ref<string>('');

const positions = ref<Position[]>([]);

async function search() {
  positions.value = [];
  positionApi.searchPosition(name.value, location.value)
    .then((ok) => {
      const urls = ok.data.urls ?? [];
      console.log("urls", urls);
      urls.map(it => it.split('/'))
        .map(it => it[it.length - 1])
        .map(it => Number.parseInt(it))
        .forEach(id => {
          console.log(id);
          positionApi.getPosition(id).then(res => {
            positions.value.push(res.data)
          });
        })
    });
}
</script>

<template>
  <div class="search-container">
    <v-form @submit.prevent="search" class="search-form">
      <v-text-field type="text" id="name" v-model="name" label="Name" variant="outlined"></v-text-field>
      <v-text-field type="text" id="location" v-model="location" label="Location" variant="outlined"></v-text-field>
      <v-btn color="primary" type="submit">
        Search
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <v-btn color="secondary">
        Add Position
        <v-icon>mdi-plus-circle</v-icon>
        <v-dialog activator="parent" v-model="showCreateDialog" class="dialog">
          <create-position @complete="showCreateDialog = false"></create-position>
        </v-dialog>
      </v-btn>
    </v-form>
    <v-divider></v-divider>
    <v-list>
      <v-list-item v-for="p in positions" :title="p.name" :subtitle="p.location"></v-list-item>
    </v-list>
  </div>
</template>

<style scoped>
.search-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  margin-top: 2rem;
}

.search-form {
  display: flex;
  gap: 1rem;
}

.dialog {
  max-width: 400px;
}
</style>
