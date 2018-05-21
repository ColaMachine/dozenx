
    Vue.component('apiParameter', {
        template: '\
            <tr  >\
            <td >\
            {{item.name}}\
            </td>\
             <td >\
            <input :name=item.name type="text" width="200px"/>\
            </td>\
             <td >\
            {{item.description}}\
            </td>\
             <td >\
            {{item.type}}\
            </td>\
              <td >\
            {{item.required}}\
            </td>\
            </tr>\
        ',

        props: ['item'],
    })