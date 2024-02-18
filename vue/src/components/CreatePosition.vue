<script setup lang="ts">
import { ApiError } from '@/api/pfinder';
import { positionApi } from '@/services';
import { ref } from 'vue';


const emit = defineEmits<{
  (event: 'complete'): void;
}>();
const errText = ref('');

const name = ref('');
const location = ref('');

const nameRules: any[] = [(it: any) => (!!it && it.length > 0 && it.length < 50) || "Name with length between 1 and 50 required!"];
const locationRules: any[] = [(it: any) => (!!it && it.length > 0 && it.length < 50) || "Location with length between 1 and 50 required!"];

const valid = ref<boolean | null>(null);

async function onBtnClicked(event: any) {
  event.preventDefault();

  positionApi.createPosition({ name: name.value, location: location.value })
  .then((ok) => {
    emit('complete');
  }, (err) => {
    const apiError = err.data as ApiError
    errText.value = apiError.errors.map((it) => it.message).join(', ');
  })
}
</script>

<template>
  <v-card variant="elevated" rounded class="position-card">
    <v-form fast-fail v-model="valid" @submit.prevent="onBtnClicked" class="client-form">
      <v-text-field type="text" id="name" v-model="name" :rules="nameRules" label="Position Name"
        variant="outlined"></v-text-field>
      <v-text-field type="text" id="email" v-model="location" :rules="locationRules" label="Location"
        variant="outlined"></v-text-field>
      <v-btn color="primary" type="submit" :disabled="!valid">Create</v-btn>
      <v-label v-if="!!errText">{{ errText }}</v-label>
    </v-form>
  </v-card>
</template>

<style scoped>
.position-card {
  padding: 1rem;
}
</style>
