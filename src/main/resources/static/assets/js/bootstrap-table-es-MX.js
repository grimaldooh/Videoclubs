/**
 * Bootstrap Table Spanish (M&eacute;xico) translation (Obtenido de traducci&oacute;n de Argentina)
 * Author: Felix Vera (felix.vera@gmail.com)
 * Copiado: Mauricio Vera (mauricioa.vera@gmail.com)
 * Revisi&oacute;n: J Manuel Corona (jmcg92@gmail.com) (13/Feb/2018).
 */

$.fn.bootstrapTable.locales['es-MX'] = {
  formatCopyRows () {
    return 'Copy Rows'
  },
  formatPrint () {
    return 'Print'
  },
  formatLoadingMessage () {
    return 'Cargando, espere por favor'
  },
  formatRecordsPerPage (pageNumber) {
    return `Mostrar ${pageNumber} registros por p&aacute;gina`
  },
  formatShowingRows (pageFrom, pageTo, totalRows, totalNotFiltered) {
    if (totalNotFiltered !== undefined && totalNotFiltered > 0 && totalNotFiltered > totalRows) {
      return `Mostrando ${pageFrom} a ${pageTo} de ${totalRows} filas (filtered from ${totalNotFiltered} total rows)`
    }

    return `Mostrando ${pageFrom} a ${pageTo} de ${totalRows} filas.`
  },
  formatSRPaginationPreText () {
    return 'previous page'
  },
  formatSRPaginationPageText (page) {
    return `to page ${page}`
  },
  formatSRPaginationNextText () {
    return 'next page'
  },
  formatDetailPagination (totalRows) {
    return `Mostrando ${totalRows} filas`
  },
  formatClearSearch () {
    return 'Limpiar b&uacute;squeda'
  },
  formatSearch () {
    return 'Buscar'
  },
  formatNoMatches () {
    return 'No se encontraron registros que coincidan'
  },
  formatPaginationSwitch () {
    return 'Mostrar/ocultar paginaci&oacute;n'
  },
  formatPaginationSwitchDown () {
    return 'Show pagination'
  },
  formatPaginationSwitchUp () {
    return 'Hide pagination'
  },
  formatRefresh () {
    return 'Actualizar'
  },
  formatToggle () {
    return 'Cambiar vista'
  },
  formatToggleOn () {
    return 'Show card view'
  },
  formatToggleOff () {
    return 'Hide card view'
  },
  formatColumns () {
    return 'Columnas'
  },
  formatColumnsToggleAll () {
    return 'Activar/Desactivar todo'
  },
  formatFullscreen () {
    return 'Pantalla completa'
  },
  formatAllRows () {
    return 'Todo'
  },
  formatAutoRefresh () {
    return 'Auto Refresh'
  },
  formatExport () {
    return 'Export data'
  },
  formatJumpTo () {
    return 'GO'
  },
  formatAdvancedSearch () {
    return 'Advanced search'
  },
  formatAdvancedCloseButton () {
    return 'Close'
  },
  formatFilterControlSwitch () {
    return 'Ocultar/Mostrar controles'
  },
  formatFilterControlSwitchHide () {
    return 'Ocultar controles'
  },
  formatFilterControlSwitchShow () {
    return 'Mostrar controles'
  }
}

$.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['es-MX'])
