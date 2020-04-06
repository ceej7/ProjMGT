<template>
  <el-table
    v-loading="loading"
    :data="data"
    @selection-change="handleSelectionChange"
    @sort-change="handleSortChange"
  >
    <template v-for="(item, $index) in columns">
      <el-table-column v-if="item.prop === 'selection'" :key="item.prop" type="selection" :width="item.width" />
      <template v-else-if="item.prop === 'options' || item.prop === 'options1' || item.prop === 'options2'">
        <template v-if="item.options.length >= 1">
          <el-table-column :key="$index" :label="item.label" :align="item.align" :width="item.width" :fixed="item.fixed">
            <template slot-scope="scope">
              <template v-if="item.icon">
                <el-dropdown trigger="click">
                  <el-button circle class="border-none">
                    <svg-icon :icon-class="item.icon" />
                  </el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item v-for="(btn, index) in item.options" :key="index">
                      <el-link
                        :key="$index"
                        :underline="false"
                        :type="btn.type"
                        @click="btn.event(scope.row)"
                      > {{ btn.label }} </el-link>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </template>
              <template v-else>
                <template v-for="(btn, index) in item.options">
                  <el-button
                    :key="index"
                    :disabled="!(item.prop === 'options2' && scope.row.canSendBack) && !((item.prop === 'options' || item.prop === 'options1') && scope.row.status === 'unfilled')"
                    size="small"
                    :type="btn.type"
                    @click="btn.event(scope.row)"
                  >{{ btn.label }}
                  </el-button>
                </template>
              </template>
            </template>
          </el-table-column>
        </template>
      </template>
      <el-table-column
        v-else-if="item.prop === 'slot'"
        :key="$index"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
        :align="item.align"
        :show-overflow-tooltip="item.tooltip ? item.tooltip : false"
        :sortable="item.sortable ? item.sortable : false"
      >
        <template #default="{row}">
          <slot :name="'tb-' + item.sign" :row="row" />
        </template>
      </el-table-column>
      <template v-else>
        <template v-if="item.isSingleFormat">
          <el-table-column
            :key="$index"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"
            :align="item.align"
            :formatter="typeof item.formatter === 'function' ? item.formatter : null"
            :show-overflow-tooltip="item.tooltip ? item.tooltip : false"
            :sortable="item.sortable ? item.sortable : false"
          >
            <template #default="{row}">
              <div :is="item.formatter" :row="row" :column="item.prop" />
            </template>
          </el-table-column>
        </template>

        <template v-else>
          <el-table-column
            :key="$index"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"
            :align="item.align"
            :formatter="item.formatter ? item.formatter : null"
            :show-overflow-tooltip="item.tooltip ? item.tooltip : false"
            :sortable="item.sortable ? item.sortable : false"
          />
        </template>
      </template>
    </template>
  </el-table>
</template>

<script>
export default {
  name: 'MTable',
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    data: {
      type: Array,
      default: () => []
    },
    columns: {
      type: Array,
      default: () => []
    },
    disabledList: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    handleSelectionChange(selection) {
      this.$emit('select', selection)
    },
    handleSortChange({ column, prop, order }) {
      this.$emit('sortChange', { column, prop, order })
    }
  }
}
</script>
