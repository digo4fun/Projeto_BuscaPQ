const API_BASE_URL = 'http://localhost:8080/api';

let currentSearchResults = [];
let currentSearchRequest = {};

document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

function initializeApp() {
    const searchForm = document.getElementById('search-form');
    searchForm.addEventListener('submit', handleSearch);
    
    showSearchPage();
}

function showSearchPage() {
    document.getElementById('search-page').classList.add('active');
    document.getElementById('stored-page').classList.remove('active');
    
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    document.querySelector('[onclick="showSearchPage()"]').classList.add('active');
}

function showStoredPage() {
    document.getElementById('search-page').classList.remove('active');
    document.getElementById('stored-page').classList.add('active');
    
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    document.querySelector('[onclick="showStoredPage()"]').classList.add('active');
    
    loadStoredProfiles();
}

async function handleSearch(event) {
    event.preventDefault();
    
    const formData = new FormData(event.target);
    const searchRequest = {
        skills: formData.get('skills'),
        yearsOfExperience: parseInt(formData.get('experience')),
        location: formData.get('location')
    };
    
    currentSearchRequest = searchRequest;
    
    showLoading(true);
    hideSearchResults();
    
    try {
        const response = await fetch(`${API_BASE_URL}/search/profiles`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(searchRequest)
        });
        
        if (!response.ok) {
            throw new Error(`Erro na busca: ${response.status}`);
        }
        
        const profiles = await response.json();
        currentSearchResults = profiles;
        
        displaySearchResults(profiles);
        showAlert('Busca realizada com sucesso!', 'success');
        
    } catch (error) {
        console.error('Erro ao buscar perfis:', error);
        showAlert('Erro ao buscar perfis. Tente novamente.', 'error');
    } finally {
        showLoading(false);
    }
}

function displaySearchResults(profiles) {
    const resultsContainer = document.getElementById('results-container');
    const searchResults = document.getElementById('search-results');
    
    if (profiles.length === 0) {
        resultsContainer.innerHTML = `
            <div class="alert alert-info">
                <i class="fas fa-info-circle"></i>
                Nenhum perfil encontrado com os critérios especificados.
            </div>
        `;
    } else {
        resultsContainer.innerHTML = profiles.map((profile, index) => `
            <div class="profile-card">
                <div class="profile-header">
                    <div class="profile-info">
                        <h4>${profile.name}</h4>
                        <div class="title">${profile.title}</div>
                        <div class="company">${profile.company}</div>
                    </div>
                    <div class="profile-selection">
                        <input type="checkbox" id="profile-${index}" data-index="${index}">
                        <label for="profile-${index}">Selecionar</label>
                    </div>
                </div>
                
                <div class="profile-details">
                    <div class="detail-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <span>${profile.location}</span>
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-calendar-alt"></i>
                        <span>${profile.experience}</span>
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-code"></i>
                        <span>${profile.skills}</span>
                    </div>
                </div>
                
                <div class="profile-actions">
                    <a href="${profile.linkedinUrl}" target="_blank" class="linkedin-link">
                        <i class="fab fa-linkedin"></i>
                        Ver Perfil no LinkedIn
                    </a>
                </div>
            </div>
        `).join('');
    }
    
    searchResults.classList.remove('hidden');
}

async function saveSelectedProfiles() {
    const checkboxes = document.querySelectorAll('#results-container input[type="checkbox"]:checked');
    
    if (checkboxes.length === 0) {
        showAlert('Selecione pelo menos um perfil para salvar.', 'error');
        return;
    }
    
    const selectedProfiles = Array.from(checkboxes).map(checkbox => {
        const index = parseInt(checkbox.dataset.index);
        return currentSearchResults[index];
    });
    
    const searchedProfile = `${currentSearchRequest.skills} - ${currentSearchRequest.yearsOfExperience} anos - ${currentSearchRequest.location}`;
    
    try {
        const savePromises = selectedProfiles.map(profile => 
            fetch(`${API_BASE_URL}/professionals?searchedProfile=${encodeURIComponent(searchedProfile)}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(profile)
            })
        );
        
        await Promise.all(savePromises);
        
        showAlert(`${selectedProfiles.length} perfil(s) salvos com sucesso!`, 'success');
        
        checkboxes.forEach(checkbox => checkbox.checked = false);
        
    } catch (error) {
        console.error('Erro ao salvar perfis:', error);
        showAlert('Erro ao salvar perfis. Tente novamente.', 'error');
    }
}

async function loadStoredProfiles() {
    const storedLoading = document.getElementById('stored-loading');
    const storedContainer = document.getElementById('stored-container');
    
    storedLoading.classList.remove('hidden');
    
    try {
        const response = await fetch(`${API_BASE_URL}/professionals`);
        
        if (!response.ok) {
            throw new Error(`Erro ao carregar perfis: ${response.status}`);
        }
        
        const professionals = await response.json();
        
        displayStoredProfiles(professionals);
        
    } catch (error) {
        console.error('Erro ao carregar perfis salvos:', error);
        storedContainer.innerHTML = `
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i>
                Erro ao carregar perfis salvos. Tente novamente.
            </div>
        `;
    } finally {
        storedLoading.classList.add('hidden');
    }
}

function displayStoredProfiles(professionals) {
    const storedContainer = document.getElementById('stored-container');
    
    if (professionals.length === 0) {
        storedContainer.innerHTML = `
            <div class="alert alert-info">
                <i class="fas fa-info-circle"></i>
                Nenhum perfil salvo encontrado. Faça uma busca e selecione perfis para salvá-los.
            </div>
        `;
    } else {
        storedContainer.innerHTML = professionals.map(professional => `
            <div class="profile-card">
                <div class="stored-info">
                    <div class="search-date">
                        <i class="fas fa-calendar"></i>
                        Salvo em: ${formatDate(professional.searchDate)}
                    </div>
                    <div class="searched-profile">
                        <i class="fas fa-search"></i>
                        Busca: ${professional.searchedProfile}
                    </div>
                </div>
                
                <div class="profile-header">
                    <div class="profile-info">
                        <h4>${professional.name}</h4>
                        <div class="title">${professional.title || 'Não informado'}</div>
                        <div class="company">${professional.company || 'Não informado'}</div>
                    </div>
                </div>
                
                <div class="profile-details">
                    <div class="detail-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <span>${professional.location || 'Não informado'}</span>
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-calendar-alt"></i>
                        <span>${professional.experience || 'Não informado'}</span>
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-code"></i>
                        <span>${professional.skills || 'Não informado'}</span>
                    </div>
                </div>
                
                <div class="profile-actions">
                    <a href="${professional.linkedinUrl}" target="_blank" class="linkedin-link">
                        <i class="fab fa-linkedin"></i>
                        Ver Perfil no LinkedIn
                    </a>
                    <button class="btn btn-danger" onclick="deleteProfessional(${professional.id})">
                        <i class="fas fa-trash"></i>
                        Excluir
                    </button>
                </div>
            </div>
        `).join('');
    }
}

async function deleteProfessional(professionalId) {
    if (!confirm('Tem certeza que deseja excluir este perfil?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/professionals/${professionalId}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error(`Erro ao excluir perfil: ${response.status}`);
        }
        
        showAlert('Perfil excluído com sucesso!', 'success');
        loadStoredProfiles();
        
    } catch (error) {
        console.error('Erro ao excluir perfil:', error);
        showAlert('Erro ao excluir perfil. Tente novamente.', 'error');
    }
}

function showLoading(show) {
    const loading = document.getElementById('loading');
    if (show) {
        loading.classList.remove('hidden');
    } else {
        loading.classList.add('hidden');
    }
}

function hideSearchResults() {
    const searchResults = document.getElementById('search-results');
    searchResults.classList.add('hidden');
}

function showAlert(message, type) {
    const existingAlert = document.querySelector('.alert');
    if (existingAlert) {
        existingAlert.remove();
    }
    
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    
    const icon = type === 'success' ? 'check-circle' : 
                 type === 'error' ? 'exclamation-circle' : 'info-circle';
    
    alertDiv.innerHTML = `
        <i class="fas fa-${icon}"></i>
        ${message}
    `;
    
    const container = document.querySelector('.container');
    container.insertBefore(alertDiv, container.firstChild);
    
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}
