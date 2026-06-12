package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

// ==========================================
// DATA MODELS
// ==========================================

data class CitizenProfile(
  val name: String = "Sovereign Shareholder",
  val certificateNumber: String = "MC-1888-7209B",
  val membershipClass: String = "First Class (Charter Citizen)",
  val shareCount: Int = 250,
  val dividendBalance: Double = 542.80,
  val cashBalance: Double = 3120.50,
  val totalInvested: Double = 12500.00
)

data class NetworkStatus(
  val mode: String, // Rail, Metro, Bus, Autonomous Shuttles, Bicycles
  val line: String,
  val status: String, // Operational, Delayed, Maintenance
  val originalSchedule: String,
  val expectedDeparture: String,
  val capacityUtilization: Int, // 0 - 100%
  val delayMinutes: Int = 0,
  val routeCode: String
)

data class HousingAsset(
  val id: String,
  val category: String, // Housing, Commercial, Industrial, Workshop
  val title: String,
  val location: String,
  val costDescription: String,
  val status: String, // Available, Leased, Applied, Under Review
  val details: String,
  val areaSqFt: Int
)

data class UtilityService(
  val id: String,
  val name: String, // Water, Electricity, Fibre, District Heating
  val meterNumber: String,
  val currentUsageValue: Double,
  val unit: String,
  val pricePerUnit: Double,
  val isBoosterEnabled: Boolean = false,
  val serviceNotice: String? = null
)

data class PublicWorkProject(
  val id: String,
  val name: String,
  val category: String, // Infrastructure, Energy, Maritime, Cyber grid
  val cost: Double, // in Millions
  val contractor: String,
  val progressPercent: Int,
  val completionForecast: String,
  val latitudeIndex: Float, // for mapping representation
  val longitudeIndex: Float,
  val description: String
)

data class InfrastructureBond(
  val id: String,
  val title: String,
  val costPerBond: Double,
  val yieldPercent: Double,
  val matureDate: String,
  val assetSecured: String,
  val ownedCount: Int,
  val fundingProgress: Int,
  val description: String
)

data class CorporateAsset(
  val name: String,
  val code: String,
  val bookValue: Double, // in Millions
  val physicalCondition: String, // Excellent, Operational, Upgrading
  val yearAcquired: Int,
  val capacityDescription: String
)

data class IndustrialPermit(
  val id: String,
  val businessName: String,
  val firmType: String, // Manufacturing, Research, Logistics, Tech Start-up
  val permitLevel: String, // Standard, Class-I Heavy, Bio-Mechanical Group
  val status: String, // Pending, Approved, Suspended
  val dateIssued: String
)

data class ApprenticeshipCourse(
  val id: String,
  val title: String,
  val guildDepartment: String, // Ironwork, Maritime-Grid, Locomotive Engine, Electric-Fibre
  val duration: String,
  val scholarshipStipend: String,
  val status: String, // Not Enrolled, Enrolled, Certified
  val progressPercent: Int = 0
)

data class ArchiveDocument(
  val id: String,
  val title: String,
  val year: String,
  val category: String, // Charter, Blueprint, Gazette, Account
  val synopsis: String,
  val documentCode: String
)

// ==========================================
// SYSTEM NOTIFICATION
// ==========================================
data class CorporationAlert(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val timestamp: String,
  val type: String // System, Urgent, Financial, Network
)

// ==========================================
// MAIN APP STATE
// ==========================================
data class MetropolitanAppState(
  val profile: CitizenProfile = CitizenProfile(),
  val alerts: List<CorporationAlert> = emptyList(),
  val networkStatus: List<NetworkStatus> = emptyList(),
  val housingAssets: List<HousingAsset> = emptyList(),
  val utilities: List<UtilityService> = emptyList(),
  val bonds: List<InfrastructureBond> = emptyList(),
  val corporateAssets: List<CorporateAsset> = emptyList(),
  val publicWorks: List<PublicWorkProject> = emptyList(),
  val industrialPermits: List<IndustrialPermit> = emptyList(),
  val apprenticeshipCourses: List<ApprenticeshipCourse> = emptyList(),
  val archives: List<ArchiveDocument> = emptyList(),
  val currentSearchQuery: String = "",
  val activeModalMessage: String? = null
)

class MetropolitanViewModel : ViewModel() {

  private val _state = MutableStateFlow(MetropolitanAppState())
  val state: StateFlow<MetropolitanAppState> = _state.asStateFlow()

  init {
    loadMockData()
    startSimulatedTelemetry()
  }

  private fun loadMockData() {
    val alerts = listOf(
      CorporationAlert(title = "Annual Metropolitan Dividend declared at £2.17 per Share.", timestamp = "10:14 AM", type = "Financial"),
      CorporationAlert(title = "Bessemer Line track maintenance completed ahead of timetable.", timestamp = "08:30 AM", type = "Network"),
      CorporationAlert(title = "Charter Revision of 1888 Digital Ledger integration successful.", timestamp = "Yesterday", type = "System")
    )

    val networks = listOf(
      NetworkStatus("Rail", "Bessemer Golden Loop", "Operational", "08:15", "08:15", 45, 0, "BGL-01"),
      NetworkStatus("Rail", "Ironbridge Express", "Delayed", "08:30", "08:48", 85, 18, "IBE-04"),
      NetworkStatus("Metro", "Pneumatic Line 1", "Operational", "08:34", "08:34", 60, 0, "PNL-12"),
      NetworkStatus("Metro", "Gothic Under-Line", "Maintenance", "08:40", "08:40", 0, 0, "GUL-06"),
      NetworkStatus("Bus", "Steam omnibus Grid 4", "Operational", "08:35", "08:37", 50, 2, "SO4-23"),
      NetworkStatus("Autonomous", "Wharf-Dock Shuttle", "Operational", "08:42", "08:42", 20, 0, "WDS-01"),
      NetworkStatus("Bicycles", "Victoria Terminus Dock", "Operational", "24/30 Plinths Available", "N/A", 80, 0, "VT-BIKE")
    )

    val housing = listOf(
      HousingAsset("H-01", "Housing", "Clerkenwell Brick Tenements", "Clerkenwell Sector", "£85.00/month", "Available", "3-room artisan tenement flat, brick masonry fireplace, modern gaslight conduits.", 680),
      HousingAsset("H-02", "Housing", "Royal Sovereign Terraces", "Albert Heights", "£180.00/month", "Leased", "6-room Victorian townhouse, beautiful marble entry facades, brass piping, iron balconies.", 1450),
      HousingAsset("H-03", "Commercial", "Merchant Exchange Hall Vaults", "Central Markets", "£450.00/month", "Available", "Underground logistics chamber, vaulted ceiling, mechanical lifts to wharf level.", 2400),
      HousingAsset("H-04", "Workshop", "Foundry Yard Workshops - No. 9", "Girders District", "£120.00/month", "Available", "Heavy workshop, 440V high-torque electric linkage, direct access to pneumatic scrap-pipe.", 900),
      HousingAsset("H-05", "Industrial", "Bessemer Boiler Complex Plot 3", "Industrial District", "£1200.00/month", "Available", "3-acre sovereign land plot cleared for heavy manufacturing or boiler plant extensions.", 13000)
    )

    val utilities = listOf(
      UtilityService("U-01", "Electricity Grid", "MTR-7080-X", 412.5, "kWh", 0.14, false, "Stable output from Battersea Dynamos"),
      UtilityService("U-02", "Pneumatic Water Booster", "MTR-2311-W", 18.2, "Gallons", 0.08, false, "High pressure steam boilers operational"),
      UtilityService("U-03", "District Steam Heating", "MTR-8812-H", 12.8, "Thermal Units", 0.25, true, "Increased throughput active due to cold rain"),
      UtilityService("U-04", "Sovereign Glass Fibre Grid", "MTR-9001-F", 128.4, "GB", 0.01, false, "Fibre optical telegraph speeds normal")
    )

    val bonds = listOf(
      InfrastructureBond("B-01", "West Rail Pneumatic Line Extension", 100.00, 4.80, "June 1936", "Locomotive Assets Group A", 12, 78, "Invest in the dual-bore pneumatic passenger tubes bridging Clerkenwell and Victoria Terminal."),
      InfrastructureBond("B-02", "Thames Tidal Hydro-Boiler Station", 250.00, 5.25, "Dec 1938", "Sovereign Waterfront Property", 5, 42, "Direct shareholder-funded expansion of the subterranean tidal turbine shafts in the Lower Thames Dockyard."),
      InfrastructureBond("B-03", "Bessemer High-Density Housing Guild Group", 50.00, 4.20, "Sept 1932", "Royal Girders Freehold Ledger", 25, 91, "Civic chartered bonds funding multi-tier heavy timber/iron tenement expansions for local industrial workers.")
    )

    val assets = listOf(
      CorporateAsset("Victoria Terminus Iron Arch", "VT-ARCH", 112.5, "Excellent", 1883, "Capacity: 60,000 citizens/day"),
      CorporateAsset("Battersea Power & Dynamo Station", "BAT-DYN", 245.0, "Upgrading", 1902, "Output: 450 Megawatts heavy load"),
      CorporateAsset("Clerkenwell Masonry Reservoirs", "CLK-RES", 85.0, "Operational", 1891, "Capacity: 12,000,000 Gallons water limit"),
      CorporateAsset("Thames Deep-Dredge Pneumatic Vaults", "THM-PNEUM", 155.0, "Excellent", 1910, "Capacity: 24 Cargo pods/minute throughput"),
      CorporateAsset("Imperial Sovereign High-Speed Grid Line", "IMP-HIGH", 380.0, "Operational", 1922, "Length: 52 miles double-gauge steel track")
    )

    val publicWorks = listOf(
      PublicWorkProject("PW-01", "Imperial High-Speed Line Dual Track Expansion", "Infrastructure", 48.5, "Consolidated Founders & Co.", 72, "Dec 2026", -0.15f, 0.45f, "Upgrading the high-speed iron railway corridor with secondary bypass loops and double-gauge sleepers."),
      PublicWorkProject("PW-02", "Battersea Dynamo Vault No. 5 Retrofit", "Energy", 22.1, "Sovereign Electric Guild", 95, "Aug 2026", 0.35f, -0.22f, "Installation of Parson steam turbines to double grid capacity for industrial workshops."),
      PublicWorkProject("PW-03", "Girders District Tenement Block G", "Housing", 15.8, "Chartered Masonry Guild", 30, "June 2027", -0.42f, -0.12f, "High-density multi-storey building incorporating central state district heating loops and internal laundry chutes.")
    )

    val permits = listOf(
      IndustrialPermit("P-101", "Albert Mechanical Steamworks", "Manufacturing", "Class-I Heavy", "Approved", "12-04-1889"),
      IndustrialPermit("P-102", "Thames Foundry Engineering", "Logistics", "Standard", "Approved", "10-11-1901"),
      IndustrialPermit("P-103", "New-Era Glass Telegraphic Co", "Research", "Bio-Mechanical Group", "Pending", "N/A")
    )

    val courses = listOf(
      ApprenticeshipCourse("C-01", "Steam Boiler Boilerplate Craftsmanship", "Locomotive Engine", "2 Years", "£12.50/week", "Not Enrolled"),
      ApprenticeshipCourse("C-02", "Glass Fibre Signal Relay Operation", "Electric-Fibre", "1.5 Years", "£15.00/week", "Not Enrolled"),
      ApprenticeshipCourse("C-03", "Heavy Structural Steel Riveting & Arch Design", "Ironwork", "3 Years", "£10.00/week", "Enrolled", 65),
      ApprenticeshipCourse("C-04", "Pneumatic Pressure Sub-Surface Maintenance", "Maritime-Grid", "2 Years", "£14.00/week", "Not Enrolled")
    )

    val documents = listOf(
      ArchiveDocument("D-01", "Sovereign Incorporation Charter of 1888", "1888", "Charter", "The foundational document signed by Royal Will granting absolute freehold and infrastructure monopoly of the city-region.", "CH-1888-A"),
      ArchiveDocument("D-02", "Victoria Terminus Grand Span Architectural Drawings", "1882", "Blueprint", "Detailed hand-drawn blueprints illustrating the structural iron arches and steam exhaust conduits of the terminal.", "BP-VT-SL3"),
      ArchiveDocument("D-03", "Annual Metropolitan General Report Vol. XIV", "1905", "Gazette", "A fully detailed accounting ledger of sovereign infrastructure investments, productivity index, and housing census.", "REP-1905-GL")
    )

    _state.update {
      it.copy(
        alerts = alerts,
        networkStatus = networks,
        housingAssets = housing,
        utilities = utilities,
        bonds = bonds,
        corporateAssets = assets,
        publicWorks = publicWorks,
        industrialPermits = permits,
        apprenticeshipCourses = courses,
        archives = documents
      )
    }
  }

  private fun startSimulatedTelemetry() {
    viewModelScope.launch {
      while (true) {
        delay(8000)
        // Simulate minor updates to the system state
        _state.update { current ->
          // Update utilities values slightly
          val updatedUtilities = current.utilities.map { util ->
            val changeFactor = (Math.random() - 0.45) * 0.4 // Tendency to rise slightly
            util.copy(currentUsageValue = Math.max(1.0, util.currentUsageValue + changeFactor))
          }

          // Randomise delays marginally to show a living grid
          val updatedNetworks = current.networkStatus.map { net ->
            if (net.status == "Delayed" && Math.random() > 0.8) {
              val newDelay = Math.max(2, net.delayMinutes + (if (Math.random() > 0.5) 1 else -1))
              net.copy(delayMinutes = newDelay, expectedDeparture = "08:${30 + newDelay}")
            } else net
          }

          // Advance public works builders!
          val updatedWorks = current.publicWorks.map { proj ->
            if (proj.progressPercent < 100 && Math.random() > 0.6) {
              proj.copy(progressPercent = Math.min(100, proj.progressPercent + 1))
            } else proj
          }

          current.copy(
            utilities = updatedUtilities,
            networkStatus = updatedNetworks,
            publicWorks = updatedWorks
          )
        }
      }
    }
  }

  // ==========================================
  // CITIZEN MUTATIVE ACTIONS (REAL SIMULATIONS)
  // ==========================================

  fun claimDividend() {
    _state.update { current ->
      if (current.profile.dividendBalance <= 0) {
        current.copy(activeModalMessage = "All declared dividends have already been transferred to your liquid capital ledger for the current distribution cycle.")
      } else {
        val claimed = current.profile.dividendBalance
        val updatedProfile = current.profile.copy(
          cashBalance = current.profile.cashBalance + claimed,
          dividendBalance = 0.0
        )
        val alert = CorporationAlert(
          title = "Claimed Dividend: +£${String.format("%.2f", claimed)} transferred to capital ledger.",
          timestamp = "Just Now",
          type = "Financial"
        )
        current.copy(
          profile = updatedProfile,
          alerts = listOf(alert) + current.alerts,
          activeModalMessage = "SUCCESS: Claim of £${String.format("%.2f", claimed)} processed. Capital transferred from Corporate Trust account to your Citizen Liquid Account. Document logged in registry."
        )
      }
    }
  }

  fun purchaseBond(bondId: String) {
    _state.update { current ->
      val bond = current.bonds.find { it.id == bondId }
      if (bond == null) return@update current

      if (current.profile.cashBalance < bond.costPerBond) {
        current.copy(activeModalMessage = "TRANSACTION REJECTED: Insufficient sovereign liquid capital balance. Each bond requires £${String.format("%.2f", bond.costPerBond)} of direct funds.")
      } else {
        val updatedBonds = current.bonds.map { b ->
          if (b.id == bondId) {
            b.copy(
              ownedCount = b.ownedCount + 1,
              fundingProgress = Math.min(100, b.fundingProgress + 2)
            )
          } else b
        }
        val updatedProfile = current.profile.copy(
          cashBalance = current.profile.cashBalance - bond.costPerBond,
          totalInvested = current.profile.totalInvested + bond.costPerBond
        )
        val alert = CorporationAlert(
          title = "Acquired 1 x ${bond.title} Unit.",
          timestamp = "Just Now",
          type = "Financial"
        )
        current.copy(
          profile = updatedProfile,
          bonds = updatedBonds,
          alerts = listOf(alert) + current.alerts,
          activeModalMessage = "PURCHASE SUCCESSFUL: You have purchased 1 bond unit of '${bond.title}' at £${String.format("%.2f", bond.costPerBond)} par value. Yield payments of ${bond.yieldPercent}% are now tied to your physical ledger accounts."
        )
      }
    }
  }

  fun applyForHousing(assetId: String) {
    _state.update { current ->
      val asset = current.housingAssets.find { it.id == assetId }
      if (asset == null) return@update current

      val updatedAssets = current.housingAssets.map { h ->
        if (h.id == assetId) {
          h.copy(status = "Applied - Peer Review")
        } else h
      }
      val alert = CorporationAlert(
        title = "Inbound lease application logged: ${asset.title}.",
        timestamp = "Just Now",
        type = "System"
      )
      current.copy(
        housingAssets = updatedAssets,
        alerts = listOf(alert) + current.alerts,
        activeModalMessage = "LEASE SUBMITTED: Your digital application for '${asset.title}' in the '${asset.location}' has been recorded. It will undergo sovereign chartered committee assessment within two solar days."
      )
    }
  }

  fun applyForIndustrialPermit(businessName: String, type: String, level: String) {
    if (businessName.isBlank()) {
      _state.update { it.copy(activeModalMessage = "VALIDATION ERROR: Please input a registered legal trade name to start the chartered permit application.") }
      return
    }

    _state.update { current ->
      val newPermit = IndustrialPermit(
        id = "P-${(100 + current.industrialPermits.size + 1)}",
        businessName = businessName,
        firmType = type,
        permitLevel = level,
        status = "Approved", // Autogrant for streamlined modern investor agency!
        dateIssued = "12-06-1926" // Matches the local time year standard
      )
      val alert = CorporationAlert(
        title = "Chartered permit issued for ${businessName}.",
        timestamp = "Just Now",
        type = "System"
      )
      current.copy(
        industrialPermits = current.industrialPermits + newPermit,
        alerts = listOf(alert) + current.alerts,
        activeModalMessage = "LICENSE GRANTED: The Corporation Board of Development has approved '${businessName}' as a certified corporate participant of level '${level}' for '${type}' activities."
      )
    }
  }

  fun enrollInApprenticeship(courseId: String) {
    _state.update { current ->
      val updatedCourses = current.apprenticeshipCourses.map { course ->
        if (course.id == courseId) {
          course.copy(status = "Enrolled", progressPercent = 0)
        } else course
      }
      val course = current.apprenticeshipCourses.find { it.id == courseId }
      val alert = CorporationAlert(
        title = "Citizen apprentice record established for ${course?.title}.",
        timestamp = "Just Now",
        type = "System"
      )
      current.copy(
        apprenticeshipCourses = updatedCourses,
        alerts = listOf(alert) + current.alerts,
        activeModalMessage = "ENROLLMENT REGISTERED: You are now bound as an Apprentice in the '${course?.guildDepartment}' guild department. Technical stipends of ${course?.scholarshipStipend} will be credited upon certification completion."
      )
    }
  }

  fun toggleBooster(utilityId: String) {
    _state.update { current ->
      val updated = current.utilities.map { util ->
        if (util.id == utilityId) {
          val newMode = !util.isBoosterEnabled
          util.copy(isBoosterEnabled = newMode)
        } else util
      }
      current.copy(utilities = updated)
    }
  }

  fun updateSearch(it: String) {
    _state.update { current ->
      current.copy(currentSearchQuery = it)
    }
  }

  fun clearModal() {
    _state.update { it.copy(activeModalMessage = null) }
  }
}
