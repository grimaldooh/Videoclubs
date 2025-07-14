class StoreInvComponent extends HTMLElement {
  constructor() {
    super();
    this.quantity = 1;
    this.store = 0;
  }

  connectedCallback() {
    this.render();
    this.loadOptions();
  }

  setQuantity(quantity) {
    this.quantity = quantity;
  }

  setStore(store) {
    this.store = store;
  }

  render() {
    this.innerHTML = `
    <div class="row">
        <div class="col-6">
            <select class="form-control" id="inputStore" name="store"
                    required></select>
        </div>
        <div class="col-4">
            <input class="form-control" name="inputInv" type="number" value="${this.quantity}"/>
        </div>

        <div class="col-2">
            <button class="btn btn-primary"><span class="fa fa-trash" onclick=""></span></button>
        </div>
    </div>
    <br />
    `;

    const input = this.querySelector('input');
    input.addEventListener('input', (event) => {
      this.setQuantity(event.target.value);
    });

    input.addEventListener('change', (event) => {
      this.setStore(event.target.value);
    });

    const removeButton = this.querySelector('button');
    removeButton.addEventListener('click', () => {
      this.remove();
    });
  }

  loadOptions() {
    const select = this.querySelector('select');
    return fetch('../store/select')
        .then(response => response.json())
        .then(data => {
            data.results.forEach(s => {
                const option = document.createElement('option');
                option.value = s.id;
                option.textContent = s.text;
                select.appendChild(option);
            })
        });
  }
}

customElements.define('store-inv-component', StoreInvComponent);