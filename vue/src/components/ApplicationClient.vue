<script setup lang="ts">
import { ref } from 'vue';

const clientName = ref('No Client');

const showCreateDialog = ref<boolean>(false);

function onClientCreated(name: string) {
  clientName.value = name;
  showCreateDialog.value = false;
}
</script>

<template>
<v-card class="my-card" variant="flat">
  <v-label>{{ clientName }}</v-label>
  <v-btn rounded color="primary">Create Client
    <v-dialog activator="parent" v-model="showCreateDialog" class="dialog">
        <create-client
          @complete="onClientCreated"
        ></create-client>
    </v-dialog>
  </v-btn>
</v-card>
</template>

<style scoped>
.my-card {
    display: flex;
    justify-content: flex-end;
    gap: 2rem;
}

.dialog {
  max-width: 400px;
}
</style>
