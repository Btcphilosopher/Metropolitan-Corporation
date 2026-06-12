package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.*
import com.example.ui.theme.*

// ==========================================
// NAVIGATION ENUMS
// ==========================================
enum class MetropolitanTab(val title: String, val icon: ImageVector) {
  CHARTER("Charter", Icons.Default.CardMembership),
  INFRASTRUCTURE("Infrastructure", Icons.Default.Construction),
  EXCHANGE("Exchange", Icons.Default.MonetizationOn),
  GUILD_ARCHIVE("Guild & Archives", Icons.Default.AccountBalance)
}

@Composable
fun IndicatorLED(label: String, ledColor: Color) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    Box(
      modifier = Modifier
        .size(6.dp)
        .background(ledColor, shape = RoundedCornerShape(3.dp))
    )
    Text(
      text = label.uppercase(),
      color = Color(0xFFFDFCF9),
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 8.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
      )
    )
  }
}

@Composable
fun GeometricBalanceHeader(
  state: MetropolitanAppState,
  isSearchActive: Boolean,
  onSearchToggle: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(MidnightBlue)
  ) {
    // 1. Institutional Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)
        .padding(top = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Monogram Circle Logo
        Box(
          modifier = Modifier
            .size(38.dp)
            .border(2.dp, Brass, shape = RoundedCornerShape(19.dp)),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .size(22.dp)
              .border(1.dp, Brass)
              .background(Color.Transparent),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "MC",
              color = Brass,
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }

        Column {
          Text(
            text = "CHARTERED INSTITUTION",
            color = Cream.copy(alpha = 0.8f),
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.sp,
              letterSpacing = 2.sp,
              fontWeight = FontWeight.Bold
            )
          )
          Text(
            text = "The Metropolitan Corporation",
            color = Brass,
            style = MaterialTheme.typography.titleMedium.copy(
              fontFamily = FontFamily.Serif,
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              letterSpacing = (-0.2).sp
            )
          )
        }
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.End
        ) {
          Text(
            text = "REG ID",
            color = Cream.copy(alpha = 0.6f),
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 8.sp,
              letterSpacing = 3.sp
            )
          )
          Text(
            text = "884-LDN-2024",
            color = Cream,
            style = MaterialTheme.typography.bodySmall.copy(
              fontFamily = FontFamily.Monospace,
              fontSize = 10.sp
            )
          )
        }

        IconButton(
          onClick = onSearchToggle,
          modifier = Modifier.size(36.dp)
        ) {
          Icon(
            imageVector = if (isSearchActive) Icons.Default.Close else Icons.Default.Search,
            contentDescription = "Search archive registries",
            tint = Brass,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    // Heavy gold line separating institutional header
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(4.dp)
        .background(Brass)
    )

    // 2. Shareholder Greeting / account banner (Parchment background)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(PaperPanel)
        .padding(horizontal = 16.dp, vertical = 12.dp)
        .drawBehind {
          // Bottom border in paper divider color
          drawLine(
            color = PaperDivider,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 1.dp.toPx()
          )
        },
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.Bottom
    ) {
      Column {
        Text(
          text = "Welcome, Shareholder",
          color = MidnightBlue,
          style = MaterialTheme.typography.headlineSmall.copy(
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
          )
        )
        Text(
          text = "ACCOUNT: ${state.profile.certificateNumber}",
          color = SteelGrey,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            letterSpacing = 1.sp
          )
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "METROPOLITAN DIVIDEND",
          color = SteelGrey,
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 8.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Bold
          )
        )
        Text(
          text = "£${String.format("%.2f", state.profile.dividendBalance)}",
          color = DeepGreen,
          style = MaterialTheme.typography.titleLarge.copy(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
        )
      }
    }

    // 3. Live Infrastructure Status Ribbon
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(MidnightBlue)
        .padding(horizontal = 16.dp, vertical = 6.dp)
        .drawBehind {
          // Bottom gold edge line
          drawLine(
            color = Brass,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 1.dp.toPx()
          )
        },
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IndicatorLED(label = "Rail Network: Optimal", ledColor = LightGreen)
      IndicatorLED(label = "Grid Load: 64%", ledColor = DarkGoldLED)
      IndicatorLED(label = "Fibre Optic: Active", ledColor = LightGreen)
    }
  }
}

@Composable
fun CustomGeometricNavBar(
  currentTab: MetropolitanTab,
  onTabSelected: (MetropolitanTab) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(64.dp)
      .background(MidnightBlue)
      .drawBehind {
        // Top border line in Brass
        drawLine(
          color = Brass,
          start = Offset(0f, 0f),
          end = Offset(size.width, 0f),
          strokeWidth = 2.dp.toPx()
        )
      }
      .navigationBarsPadding(),
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically
  ) {
    MetropolitanTab.values().forEachIndexed { index, tab ->
      val isSelected = currentTab == tab
      val tintColor = if (isSelected) Brass else Cream.copy(alpha = 0.5f)

      Column(
        modifier = Modifier
          .fillMaxHeight()
          .clickable(
            onClick = { onTabSelected(tab) },
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = true)
          )
          .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        // Geometric Icon Box based on index
        Box(
          modifier = Modifier
            .size(20.dp)
            .border(
              width = if (isSelected) 1.5.dp else 1.dp,
              color = tintColor,
              shape = when (index) {
                2 -> RoundedCornerShape(10.dp) // Circle shape for Exchange
                else -> RoundedCornerShape(0.dp) // Square / diamond shapes
              }
            )
            .graphicsLayer {
              rotationZ = when (index) {
                0 -> 45f // Home / Charter -> Rotated 45deg
                3 -> 12f // Archives -> Rotated 12deg
                else -> 0f // Others normal
              }
            },
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .size(4.dp)
              .background(
                color = if (isSelected) Brass else Color.Transparent,
                shape = RoundedCornerShape(2.dp)
              )
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = when (tab) {
            MetropolitanTab.CHARTER -> "Home"
            MetropolitanTab.INFRASTRUCTURE -> "Registry"
            MetropolitanTab.EXCHANGE -> "Exchange"
            MetropolitanTab.GUILD_ARCHIVE -> "Archives"
          }.uppercase(),
          color = tintColor,
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        )
      }
    }
  }
}

@Composable
fun MetropolitanApp(
  viewModel: MetropolitanViewModel = viewModel(),
  modifier: Modifier = Modifier
) {
  val state by viewModel.state.collectAsState()
  var currentTab by rememberSaveable { mutableStateOf(MetropolitanTab.CHARTER) }
  var selectedDocKey by remember { mutableStateOf<String?>(null) }
  var selectedProjectKey by remember { mutableStateOf<String?>(null) }

  // Search input state
  var isSearchActive by rememberSaveable { mutableStateOf(false) }

  // Custom styling border helper
  val brassBorderModifier = Modifier.drawBehind {
    val outerStrokeWidth = 4f
    val innerStrokeWidth = 1.5f
    val spacing = 6f
    // Draw outer golden border
    drawRect(
      color = BrassMuted,
      topLeft = Offset(outerStrokeWidth / 2, outerStrokeWidth / 2),
      size = Size(size.width - outerStrokeWidth, size.height - outerStrokeWidth),
      style = Stroke(width = outerStrokeWidth)
    )
    // Draw inner subtle border
    drawRect(
      color = Brass,
      topLeft = Offset(outerStrokeWidth + spacing, outerStrokeWidth + spacing),
      size = Size(size.width - (outerStrokeWidth + spacing) * 2, size.height - (outerStrokeWidth + spacing) * 2),
      style = Stroke(width = innerStrokeWidth)
    )
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      GeometricBalanceHeader(
        state = state,
        isSearchActive = isSearchActive,
        onSearchToggle = { isSearchActive = !isSearchActive }
      )
    },
    bottomBar = {
      CustomGeometricNavBar(
        currentTab = currentTab,
        onTabSelected = { currentTab = it }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
      ) {
        // Expandable Search Bar
        AnimatedVisibility(
          visible = isSearchActive,
          enter = expandVertically() + fadeIn(),
          exit = shrinkVertically() + fadeOut()
        ) {
          Surface(
            color = MidnightBlue,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, BrassMuted)
          ) {
            OutlinedTextField(
              value = state.currentSearchQuery,
              onValueChange = { viewModel.updateSearch(it) },
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("archive_search_input"),
              placeholder = { Text("Query architectural records, patents, charter acts...", style = MaterialTheme.typography.bodyMedium, color = SteelGrey) },
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Brass,
                unfocusedBorderColor = SteelGrey,
                focusedTextColor = Cream,
                unfocusedTextColor = Cream,
                cursorColor = Brass
              ),
              leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Brass) },
              trailingIcon = {
                if (state.currentSearchQuery.isNotEmpty()) {
                  IconButton(onClick = { viewModel.updateSearch("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = SteelGrey)
                  }
                }
              },
              singleLine = true
            )
          }
        }

        // Active layout chooser
        Crossfade(targetState = currentTab, modifier = Modifier.weight(1f)) { tab ->
          when (tab) {
            MetropolitanTab.CHARTER -> CharterScreen(state = state, onClaimDividend = { viewModel.claimDividend() })
            MetropolitanTab.INFRASTRUCTURE -> InfrastructureScreen(
              state = state,
              onToggleBooster = { viewModel.toggleBooster(it) }
            )
            MetropolitanTab.EXCHANGE -> ExchangeScreen(
              state = state,
              onBuyBond = { viewModel.purchaseBond(it) },
              onApplyHousing = { viewModel.applyForHousing(it) }
            )
            MetropolitanTab.GUILD_ARCHIVE -> GuildAndArchivesScreen(
              state = state,
              onRegisterPermit = { name, type, lvl -> viewModel.applyForIndustrialPermit(name, type, lvl) },
              onEnrollCourse = { viewModel.enrollInApprenticeship(it) },
              selectedDocId = selectedDocKey,
              onSelectDoc = { selectedDocKey = it },
              selectedProjId = selectedProjectKey,
              onSelectProj = { selectedProjectKey = it }
            )
          }
        }
      }

      // GLOBAL CUSTOM METROPOLITAN DIALOG FOR SYSTEM TRANSACTIONS / NOTICES
      state.activeModalMessage?.let { msg ->
        Dialog(onDismissRequest = { viewModel.clearModal() }) {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentHeight()
              .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(2.dp, Brass)
          ) {
            Column(
              modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              // Stately crested header
              Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = "Certified Seal",
                tint = Brass,
                modifier = Modifier.size(52.dp).padding(bottom = 8.dp)
              )
              Text(
                text = "BOARD COGNIZANCE REGISTRY",
                style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 2.sp),
                textAlign = TextAlign.Center
              )
              Spacer(modifier = Modifier.height(4.dp))
              HorizontalDivider(color = BrassMuted, thickness = 1.dp)
              Spacer(modifier = Modifier.height(16.dp))
              Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.5.sp),
                color = Cream,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
              )
              Spacer(modifier = Modifier.height(24.dp))
              Button(
                onClick = { viewModel.clearModal() },
                colors = ButtonDefaults.buttonColors(containerColor = Brass, contentColor = MidnightBlue),
                modifier = Modifier
                  .widthIn(min = 150.dp)
                  .testTag("modal_dismiss_button"),
                shape = RoundedCornerShape(3.dp)
              ) {
                Text(
                  text = "ACKNOWLEDGE STATUS",
                  style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
              }
            }
          }
        }
      }
    }
  }
}

// ==========================================
// 1. HOME SCREEN: THE CHARTER
// ==========================================
@Composable
fun CharterScreen(
  state: MetropolitanAppState,
  onClaimDividend: () -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Elegant Victorian Stamped Greeting
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(DarkSurface)
          .drawBehind {
            val stroke = 3f
            val space = 4.dp.toPx()
            // Custom double border in gold inside the box
            drawRect(
              color = BrassMuted,
              topLeft = Offset(space, space),
              size = Size(size.width - space * 2, size.height - space * 2),
              style = Stroke(width = stroke)
            )
          }
          .padding(24.dp)
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
          Text(
            text = "SOVEREIGN CITIZEN STATUS JOURNAL",
            style = MaterialTheme.typography.labelMedium.copy(color = BrassMuted, letterSpacing = 3.sp)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Welcome, Shareholder.",
            style = MaterialTheme.typography.displayMedium.copy(color = Brass),
            textAlign = TextAlign.Center
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "YOUR PARTICIPATION INSURES PROGRESS • INTEGRITY • PUBLIC WEALTH",
            style = MaterialTheme.typography.labelSmall.copy(color = SteelGrey, letterSpacing = 1.2.sp),
            textAlign = TextAlign.Center
          )
          Spacer(modifier = Modifier.height(18.dp))
          HorizontalDivider(color = Brass, thickness = 1.dp, modifier = Modifier.width(180.dp))
          Spacer(modifier = Modifier.height(16.dp))

          // Mini account grid
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "SHARE CERTIFICATE ID", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
              Text(
                text = state.profile.certificateNumber,
                style = MaterialTheme.typography.labelMedium,
                color = Cream
              )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "TRUST REGISTER", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
              Text(
                text = state.profile.membershipClass,
                style = MaterialTheme.typography.labelMedium,
                color = Cream
              )
            }
          }
        }
      }
    }

    // Capital & Dividend Ledger Balance Sheets
    item {
      Column {
        Text(
          text = "MUNICIPAL ASSET ACCOUNTING",
          style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp),
          modifier = Modifier.padding(bottom = 6.dp)
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          // Sovereign Dividends Card
          Card(
            modifier = Modifier
              .weight(1f)
              .height(160.dp),
            colors = CardDefaults.cardColors(containerColor = DeepGreen),
            border = BorderStroke(1.5.dp, Brass)
          ) {
            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
              verticalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "METROPOLITAN DIVIDEND",
                  style = MaterialTheme.typography.labelSmall.copy(color = Brass, fontWeight = FontWeight.Bold)
                )
                Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = Brass, modifier = Modifier.size(16.dp))
              }
              Column {
                Text(
                  text = "£${String.format("%.2f", state.profile.dividendBalance)}",
                  style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp, color = Cream)
                )
                Text(
                  text = "From ${state.profile.shareCount} Sovereign Shares",
                  style = MaterialTheme.typography.labelSmall,
                  color = Cream.copy(alpha = 0.7f)
                )
              }
              Button(
                onClick = onClaimDividend,
                colors = ButtonDefaults.buttonColors(containerColor = Brass, contentColor = MidnightBlue),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(34.dp)
                  .testTag("claim_dividend_button"),
                shape = RoundedCornerShape(2.dp),
                contentPadding = PaddingValues(0.dp)
              ) {
                Text("CLAIM TO CAPITAL", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
              }
            }
          }

          // Direct Liquid Assets
          Card(
            modifier = Modifier
              .weight(1f)
              .height(160.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, SteelGrey)
          ) {
            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
              verticalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "LIQUID LIABILITIES",
                  style = MaterialTheme.typography.labelSmall.copy(color = SteelGrey)
                )
                Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = SteelGrey, modifier = Modifier.size(16.dp))
              }
              Column {
                Text(
                  text = "£${String.format("%.2f", state.profile.cashBalance)}",
                  style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp, fontFamily = FontFamily.Monospace, color = Brass)
                )
                Text(
                  text = "Available Capital",
                  style = MaterialTheme.typography.labelSmall,
                  color = SteelGrey
                )
              }
              // Decorative trust seal tag
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .border(BorderStroke(1.dp, SteelGrey.copy(alpha = 0.4f)))
                  .padding(vertical = 4.dp, horizontal = 8.dp)
              ) {
                Text(
                  text = "INVESTED: £${String.format("%.0f", state.profile.totalInvested)}",
                  style = MaterialTheme.typography.labelSmall,
                  color = SteelGrey,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.fillMaxWidth()
                )
              }
            }
          }
        }
      }
    }

    // Corporate Bulletins & Gazettes
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "SOVEREIGN GAZETTE & URGENT NOTICES",
            style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
          )
          Text(
            text = "LIVE REGISTRY",
            style = MaterialTheme.typography.labelSmall.copy(color = LightGreen)
          )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Bulletins list
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = DarkSurface),
          border = BorderStroke(1.dp, BrassMuted)
        ) {
          Column {
            state.alerts.forEachIndexed { idx, alert ->
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                      modifier = Modifier
                        .size(6.dp)
                        .background(if (alert.type == "Urgent") RustRed else Brass)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = alert.type.uppercase(),
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = if (alert.type == "Urgent") RustRed else Brass,
                        fontWeight = FontWeight.Bold
                      )
                    )
                  }
                  Text(text = alert.timestamp, style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = alert.title,
                  style = MaterialTheme.typography.bodyMedium,
                  color = Cream
                )
              }
              if (idx < state.alerts.lastIndex) {
                HorizontalDivider(color = BrassMuted.copy(alpha = 0.3f), thickness = 1.dp)
              }
            }
          }
        }
      }
    }

    // Modern Infrastructure Stats Summary
    item {
      Column {
        Text(
          text = "CIVIC PERFORMANCE INDEX (ESTIMATED)",
          style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp),
          modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = DarkSurface),
          border = BorderStroke(1.dp, SteelGrey)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("RAIL TIMETABLE COMPLIANCE", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
              Text("97.2%", style = MaterialTheme.typography.labelSmall.copy(color = LightGreen, fontWeight = FontWeight.Bold))
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
              progress = 0.972f,
              color = LightGreen,
              trackColor = DarkBackground,
              modifier = Modifier.fillMaxWidth().height(4.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("STEEL YIELD & CONSTRUCTION RATING", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
              Text("EXCELLENT (A1)", style = MaterialTheme.typography.labelSmall.copy(color = Brass))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Municipal assets currently aggregate £4,892,100 in Book Value across 5 major complexes.",
              style = MaterialTheme.typography.bodySmall,
              color = SteelGrey
            )
          }
        }
      }
    }
  }
}

// ==========================================
// 2. INFRASTRUCTURE DASHBOARD (TRANSIT, UTILITIES)
// ==========================================
@Composable
fun InfrastructureScreen(
  state: MetropolitanAppState,
  onToggleBooster: (String) -> Unit
) {
  var selectedSubSection by remember { mutableStateOf("TRANSIT") }

  Column(modifier = Modifier.fillMaxSize()) {
    // Subsection switches (Victorian brass control buttons)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(DarkSurface)
        .padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Button(
        onClick = { selectedSubSection = "TRANSIT" },
        colors = ButtonDefaults.buttonColors(
          containerColor = if (selectedSubSection == "TRANSIT") Brass else DarkSurfaceVariant,
          contentColor = if (selectedSubSection == "TRANSIT") MidnightBlue else Brass
        ),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Brass),
        modifier = Modifier.weight(1f)
      ) {
        Text("PNEUMATIC TRANSIT", style = MaterialTheme.typography.labelLarge)
      }
      Button(
        onClick = { selectedSubSection = "UTILITIES" },
        colors = ButtonDefaults.buttonColors(
          containerColor = if (selectedSubSection == "UTILITIES") Brass else DarkSurfaceVariant,
          contentColor = if (selectedSubSection == "UTILITIES") MidnightBlue else Brass
        ),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Brass),
        modifier = Modifier.weight(1f)
      ) {
        Text("GRID UTILITIES", style = MaterialTheme.typography.labelLarge)
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      if (selectedSubSection == "TRANSIT") {
        item {
          // Custom Drawn Transit Grid Map representation to showcase "Craftsmanship"
          Column {
            Text(
              text = "METROPOLITAN TRANSIT VECTOR LINKS",
              style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(MidnightBlue)
                .border(BorderStroke(1.5.dp, BrassMuted))
                .drawBehind {
                  // Draw engineering coordinates grid lines
                  val gridSpacing = 40f
                  val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

                  for (x in 0..(size.width / gridSpacing).toInt()) {
                    drawLine(
                      color = SteelGrey.copy(alpha = 0.25f),
                      start = Offset(x * gridSpacing, 0f),
                      end = Offset(x * gridSpacing, size.height),
                      pathEffect = pathEffect
                    )
                  }
                  for (y in 0..(size.height / gridSpacing).toInt()) {
                    drawLine(
                      color = SteelGrey.copy(alpha = 0.25f),
                      start = Offset(0f, y * gridSpacing),
                      end = Offset(size.width, y * gridSpacing),
                      pathEffect = pathEffect
                    )
                  }

                  // Draw transit connections (pneumatic loop tubes)
                  drawCircle(color = Brass, radius = 8f, center = Offset(size.width * 0.15f, size.height * 0.45f))
                  drawCircle(color = Brass, radius = 8f, center = Offset(size.width * 0.55f, size.height * 0.25f))
                  drawCircle(color = Brass, radius = 8f, center = Offset(size.width * 0.85f, size.height * 0.45f))
                  drawCircle(color = Brass, radius = 8f, center = Offset(size.width * 0.45f, size.height * 0.75f))

                  drawLine(
                    color = Brass,
                    start = Offset(size.width * 0.15f, size.height * 0.45f),
                    end = Offset(size.width * 0.55f, size.height * 0.25f),
                    strokeWidth = 4f
                  )
                  drawLine(
                    color = Brass,
                    start = Offset(size.width * 0.55f, size.height * 0.25f),
                    end = Offset(size.width * 0.85f, size.height * 0.45f),
                    strokeWidth = 4f
                  )
                  drawLine(
                    color = LightGreen,
                    start = Offset(size.width * 0.15f, size.height * 0.45f),
                    end = Offset(size.width * 0.45f, size.height * 0.75f),
                    strokeWidth = 6f // Highlight active line
                  )
                  drawLine(
                    color = LightGreen,
                    start = Offset(size.width * 0.45f, size.height * 0.75f),
                    end = Offset(size.width * 0.85f, size.height * 0.45f),
                    strokeWidth = 6f
                  )

                  // Gothic Underline Tube (Dotted/Maintenance)
                  drawLine(
                    color = RustRed,
                    start = Offset(size.width * 0.55f, size.height * 0.25f),
                    end = Offset(size.width * 0.45f, size.height * 0.75f),
                    strokeWidth = 3f,
                    pathEffect = pathEffect
                  )
                }
            ) {
              // Custom text tags overlaying the vector map to make it super elegant
              Text(
                "VICTORIA TERMINUS",
                modifier = Modifier.padding(start = 12.dp, top = 92.dp),
                style = MaterialTheme.typography.labelSmall.copy(color = BrassMuted, fontWeight = FontWeight.Bold, background = DarkBackground.copy(alpha = 0.8f))
              )
              Text(
                "ALBERT BRIDGE TOWER",
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 10.dp),
                style = MaterialTheme.typography.labelSmall.copy(color = Brass, background = DarkBackground.copy(alpha = 0.8f))
              )
              Text(
                "IRONWORKS DOCKS",
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp),
                style = MaterialTheme.typography.labelSmall.copy(color = LightGreen, background = DarkBackground.copy(alpha = 0.8f))
              )
            }
          }
        }

        item {
          // Railway & Metro Live split-flap timetables
          Text(
            text = "SOVEREIGN SYSTEM DEPARTURE BOARDS",
            style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
          )
          Spacer(modifier = Modifier.height(4.dp))

          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, BrassMuted)
          ) {
            Column {
              state.networkStatus.forEachIndexed { index, net ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // Graphic type indicator
                  Box(
                    modifier = Modifier
                      .size(40.dp)
                      .background(if (net.status == "Operational") DeepGreen else if (net.status == "Maintenance") RustRed else Color(0x22FFFFFF))
                      .border(BorderStroke(1.dp, BrassMuted)),
                    contentAlignment = Alignment.Center
                  ) {
                    val icon = when (net.mode) {
                      "Rail" -> Icons.Default.DirectionsTransit
                      "Metro" -> Icons.Default.Subway
                      "Bus" -> Icons.Default.DirectionsBus
                      else -> Icons.Default.DirectionsBike
                    }
                    Icon(icon, contentDescription = net.mode, tint = Brass, modifier = Modifier.size(20.dp))
                  }

                  Spacer(modifier = Modifier.width(12.dp))

                  // Information section
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = net.line.uppercase(),
                      style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                      color = Cream,
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(text = net.routeCode, style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                      Spacer(modifier = Modifier.width(8.dp))
                      Box(modifier = Modifier.size(6.dp).clip(RoundedCornerShape(3.dp)).background(if (net.status == "Operational") LightGreen else RustRed))
                      Spacer(modifier = Modifier.width(4.dp))
                      Text(text = net.status.uppercase(), style = MaterialTheme.typography.labelSmall, color = if (net.status == "Operational") LightGreen else RustRed)
                    }
                  }

                  // Times and capacity utilization
                  Column(horizontalAlignment = Alignment.End) {
                    Text(
                      text = "SCHED: ${net.originalSchedule}",
                      style = MaterialTheme.typography.labelSmall,
                      color = SteelGrey
                    )
                    Text(
                      text = "EXPECT: ${net.expectedDeparture}",
                      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                      color = if (net.delayMinutes > 0) RustRed else LightGreen
                    )
                    Text(
                      text = "CAPACY: ${net.capacityUtilization}%",
                      style = MaterialTheme.typography.labelSmall,
                      color = when {
                        net.capacityUtilization > 80 -> RustRed
                        net.capacityUtilization > 50 -> Brass
                        else -> LightGreen
                      }
                    )
                  }
                }
                if (index < state.networkStatus.lastIndex) {
                  HorizontalDivider(color = BrassMuted.copy(alpha = 0.3f), thickness = 1.dp)
                }
              }
            }
          }
        }
      } else {
        // UTILITIES SECTION
        item {
          Text(
            text = "SOVEREIGN RESOURCES SMART POWER & WATER GRID",
            style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
          )
          Spacer(modifier = Modifier.height(4.dp))

          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, BrassMuted)
          ) {
            Column {
              state.utilities.forEachIndexed { index, util ->
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Column {
                      Text(text = util.name.uppercase(), style = MaterialTheme.typography.titleMedium, color = Brass)
                      Text(text = "METER NO: ${util.meterNumber}", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                    }

                    // Interactive Turbo booster switch (gorgeous brass lever look)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(
                        text = "TURBO STEAM",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (util.isBoosterEnabled) LightGreen else SteelGrey
                      )
                      Spacer(modifier = Modifier.width(6.dp))
                      Switch(
                        checked = util.isBoosterEnabled,
                        onCheckedChange = { onToggleBooster(util.id) },
                        colors = SwitchDefaults.colors(
                          checkedThumbColor = Brass,
                          checkedTrackColor = DeepGreen,
                          uncheckedThumbColor = SteelGrey,
                          uncheckedTrackColor = DarkBackground
                        )
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(8.dp))

                  // Live Meter readouts with realistic tick details
                  Row(
                    modifier = Modifier
                      .fillMaxWidth()
                      .background(DarkBackground)
                      .border(BorderStroke(1.dp, SteelGrey.copy(alpha = 0.4f)))
                      .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Column {
                      Text("AGGREGATE MONTHLY LOAD", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                      Text(
                        text = "${String.format("%.1f", util.currentUsageValue)} ${util.unit}",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp, fontFamily = FontFamily.Monospace),
                        color = Cream
                      )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                      Text("RATE TARIFF", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                      Text(
                        text = "£${util.pricePerUnit}/${util.unit}",
                        style = MaterialTheme.typography.labelLarge.copy(fontFamily = FontFamily.Monospace),
                        color = Brass
                      )
                    }
                  }

                  util.serviceNotice?.let { notice ->
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                      text = "✦ REPORTED DATA: $notice",
                      style = MaterialTheme.typography.labelSmall.copy(color = LightGreen)
                    )
                  }
                }
                if (index < state.utilities.lastIndex) {
                  HorizontalDivider(color = BrassMuted.copy(alpha = 0.3f), thickness = 1.dp)
                }
              }
            }
          }
        }
      }
    }
  }
}

// ==========================================
// 3. CAPITAL & LEASES EXCHANGE (BONDS, HOUSING)
// ==========================================
@Composable
fun ExchangeScreen(
  state: MetropolitanAppState,
  onBuyBond: (String) -> Unit,
  onApplyHousing: (String) -> Unit
) {
  var selectedSubMode by remember { mutableStateOf("BONDS") }

  Column(modifier = Modifier.fillMaxSize()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(DarkSurface)
        .padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Button(
        onClick = { selectedSubMode = "BONDS" },
        colors = ButtonDefaults.buttonColors(
          containerColor = if (selectedSubMode == "BONDS") Brass else DarkSurfaceVariant,
          contentColor = if (selectedSubMode == "BONDS") MidnightBlue else Brass
        ),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Brass),
        modifier = Modifier.weight(1f)
      ) {
        Text("INFRASTRUCTURE BONDS", style = MaterialTheme.typography.labelLarge)
      }
      Button(
        onClick = { selectedSubMode = "HOUSING" },
        colors = ButtonDefaults.buttonColors(
          containerColor = if (selectedSubMode == "HOUSING") Brass else DarkSurfaceVariant,
          contentColor = if (selectedSubMode == "HOUSING") MidnightBlue else Brass
        ),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Brass),
        modifier = Modifier.weight(1f)
      ) {
        Text("LEASE MARKETPLACE", style = MaterialTheme.typography.labelLarge)
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      if (selectedSubMode == "BONDS") {
        item {
          Text(
            text = "SOVEREIGN DEVELOPMENT EXCHANGE BOND OFFERS",
            style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Underwrite local municipal programs for direct yield payouts.",
            style = MaterialTheme.typography.bodySmall,
            color = SteelGrey
          )
        }

        items(state.bonds) { bond ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, Brass)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = bond.title.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Brass
                  )
                  Text(
                    text = "SECTOR SECURED: ${bond.assetSecured}",
                    style = MaterialTheme.typography.labelSmall,
                    color = SteelGrey
                  )
                }

                Box(
                  modifier = Modifier
                    .background(DeepGreen)
                    .border(BorderStroke(1.dp, Brass))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                  Text(
                    text = "${bond.yieldPercent}% YIELD",
                    style = MaterialTheme.typography.labelLarge.copy(color = Cream)
                  )
                }
              }

              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = bond.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Cream
              )
              Spacer(modifier = Modifier.height(12.dp))

              // Stats Row (Monospace)
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .background(DarkBackground)
                  .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column {
                  Text("BOND PAR VALUE", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  Text("£${String.format("%.2f", bond.costPerBond)}", style = MaterialTheme.typography.labelMedium, color = Brass)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text("YOUR PORTFOLIO", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  Text("${bond.ownedCount} Certificates", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Cream)
                }
                Column(horizontalAlignment = Alignment.End) {
                  Text("MATURATION DATE", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  Text(bond.matureDate.uppercase(), style = MaterialTheme.typography.labelMedium, color = Cream)
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Funding progress
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "SUBSCRIBED CAPITAL PROGRESS", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                Text(text = "${bond.fundingProgress}% FUNDED", style = MaterialTheme.typography.labelSmall, color = Brass)
              }
              Spacer(modifier = Modifier.height(4.dp))
              LinearProgressIndicator(
                progress = bond.fundingProgress / 100f,
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = Brass,
                trackColor = DarkBackground
              )

              Spacer(modifier = Modifier.height(14.dp))

              Button(
                onClick = { onBuyBond(bond.id) },
                colors = ButtonDefaults.buttonColors(containerColor = Brass, contentColor = MidnightBlue),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("buy_bond_${bond.id}"),
                shape = RoundedCornerShape(2.dp)
              ) {
                Text(
                  text = "UNDERWRITE DIRECT BOND (Cost: £${String.format("%.2f", bond.costPerBond)})",
                  style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
              }
            }
          }
        }
      } else {
        // HOUSING LEASE MARKETPLACE
        item {
          Text(
            text = "CORPORATIVE HOUSING & INDUSTRIAL REAL ESTATE LEASE REGISTRIES",
            style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "All properties are direct holdings of the Metropolitan General Freeholder Assembly.",
            style = MaterialTheme.typography.bodySmall,
            color = SteelGrey
          )
        }

        items(state.housingAssets) { asset ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = BorderStroke(1.dp, BrassMuted)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
              ) {
                Column {
                  Text(
                    text = asset.title.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Brass
                  )
                  Text(
                    text = "${asset.location.uppercase()} • ${asset.areaSqFt} SQ FT",
                    style = MaterialTheme.typography.labelSmall,
                    color = SteelGrey
                  )
                }

                Box(
                  modifier = Modifier
                    .background(if (asset.status == "Available") DeepGreen else RustRed)
                    .border(BorderStroke(1.dp, Brass))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                  Text(
                    text = asset.status.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(color = Cream, fontWeight = FontWeight.Bold)
                  )
                }
              }

              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = asset.details,
                style = MaterialTheme.typography.bodyMedium,
                color = Cream
              )
              Spacer(modifier = Modifier.height(14.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text("MUNICIPAL TARIFF", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  Text(
                    text = asset.costDescription,
                    style = MaterialTheme.typography.titleLarge,
                    color = Brass
                  )
                }

                if (asset.status == "Available") {
                  Button(
                    onClick = { onApplyHousing(asset.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Brass, contentColor = MidnightBlue),
                    modifier = Modifier.testTag("apply_housing_${asset.id}"),
                    shape = RoundedCornerShape(2.dp)
                  ) {
                    Text("APPLY LEASE AGREEMENT", style = MaterialTheme.typography.labelLarge)
                  }
                } else {
                  Button(
                    onClick = {},
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(disabledContainerColor = DarkBackground),
                    shape = RoundedCornerShape(3.dp)
                  ) {
                    Text("LEASE SECURED", style = MaterialTheme.typography.labelLarge, color = SteelGrey)
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

// ==========================================
// 4. GUILDS & DIGITAL ARCHIVE GATEWAY
// ==========================================
@Composable
fun GuildAndArchivesScreen(
  state: MetropolitanAppState,
  onRegisterPermit: (String, String, String) -> Unit,
  onEnrollCourse: (String) -> Unit,
  selectedDocId: String?,
  onSelectDoc: (String?) -> Unit,
  selectedProjId: String?,
  onSelectProj: (String?) -> Unit
) {
  var activeBranch by remember { mutableStateOf("GUILDS") } // GUILDS or ARCHIVES or PROJECTS
  var newPermitName by remember { mutableStateOf("") }
  var chosenType by remember { mutableStateOf("Manufacturing") }
  var chosenLevel by remember { mutableStateOf("Standard") }

  Column(modifier = Modifier.fillMaxSize()) {
    // Elegant tab selector
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(DarkSurface)
        .horizontalScroll(rememberScrollState())
        .padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      listOf("GUILDS", "PROJECTS", "ARCHIVES").forEach { branch ->
        val isActive = activeBranch == branch
        Button(
          onClick = { activeBranch = branch },
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) Brass else DarkSurfaceVariant,
            contentColor = if (isActive) MidnightBlue else Brass
          ),
          border = BorderStroke(1.dp, Brass),
          shape = RoundedCornerShape(2.dp)
        ) {
          Text(branch, style = MaterialTheme.typography.labelLarge)
        }
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      when (activeBranch) {
        "GUILDS" -> {
          // TECHNICAL APPRENTICESHIPS & INDUSTRIAL Guilds
          item {
            Text(
              text = "ENGINEERING GUILD APPRENTICE PROGRAMME",
              style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Submit to local master instructions to verify machinery/telegraph skills.",
              style = MaterialTheme.typography.bodySmall,
              color = SteelGrey
            )
          }

          items(state.apprenticeshipCourses) { course ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = DarkSurface),
              border = BorderStroke(1.dp, BrassMuted)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.Top
                ) {
                  Column {
                    Text(text = course.title.uppercase(), style = MaterialTheme.typography.titleMedium, color = Brass)
                    Text(text = "DEPARTMENT: ${course.guildDepartment.uppercase()}", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  }

                  Box(
                    modifier = Modifier
                      .background(if (course.status == "Enrolled") DeepGreen else DarkBackground)
                      .border(BorderStroke(1.dp, Brass))
                      .padding(vertical = 4.dp, horizontal = 8.dp)
                  ) {
                    Text(text = course.status.uppercase(), style = MaterialTheme.typography.labelSmall, color = Cream)
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Column {
                    Text("APPRENTICE SCHOLARSHIP STIPEND", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                    Text(course.scholarshipStipend, style = MaterialTheme.typography.labelMedium.copy(fontFamily = FontFamily.Monospace), color = LightGreen)
                  }
                  Column(horizontalAlignment = Alignment.End) {
                    Text("MINIMUM TERM", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                    Text(course.duration, style = MaterialTheme.typography.labelMedium, color = Cream)
                  }
                }

                if (course.status == "Enrolled") {
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(text = "TECHNICAL TRAINING COMPLIANCE", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  Spacer(modifier = Modifier.height(2.dp))
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    LinearProgressIndicator(
                      progress = course.progressPercent / 100f,
                      modifier = Modifier.weight(1f).height(6.dp),
                      color = LightGreen,
                      trackColor = DarkBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${course.progressPercent}%", style = MaterialTheme.typography.labelSmall, color = LightGreen)
                  }
                }

                if (course.status == "Not Enrolled") {
                  Spacer(modifier = Modifier.height(12.dp))
                  Button(
                    onClick = { onEnrollCourse(course.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Brass, contentColor = MidnightBlue),
                    modifier = Modifier
                      .fillMaxWidth()
                      .testTag("enroll_course_${course.id}"),
                    shape = RoundedCornerShape(2.dp)
                  ) {
                    Text("BIND TO APPRENTICESHIP CONTRACT")
                  }
                }
              }
            }
          }

          // REGISTER INDUSTRIAL PERMITS & BUSINESS LICENSES
          item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = "BOARD OF COMMERCE INDUSTRIAL PERMIT REGISTRY",
              style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = DarkSurface),
              border = BorderStroke(2.dp, Brass)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Text(
                  text = "APPLY NEW TRADING LICENSE",
                  style = MaterialTheme.typography.titleLarge.copy(color = Brass),
                  modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                  value = newPermitName,
                  onValueChange = { newPermitName = it },
                  label = { Text("Industrial Firm Legal Name", color = SteelGrey) },
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = SteelGrey,
                    focusedTextColor = Cream,
                    unfocusedTextColor = Cream
                  ),
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("new_permit_name_input"),
                  singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Firm type selection
                Text("Operational Domain", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  listOf("Manufacturing", "Research", "Logistics").forEach { type ->
                    val isChosen = chosenType == type
                    Button(
                      onClick = { chosenType = type },
                      colors = ButtonDefaults.buttonColors(
                        containerColor = if (isChosen) Brass else DarkSurfaceVariant,
                        contentColor = if (isChosen) MidnightBlue else Brass
                      ),
                      modifier = Modifier.weight(1f),
                      shape = RoundedCornerShape(1.dp),
                      border = BorderStroke(1.dp, Brass),
                      contentPadding = PaddingValues(0.dp)
                    ) {
                      Text(text = type, style = MaterialTheme.typography.labelSmall)
                    }
                  }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // License category level selection
                Text("Safety Group Category", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  listOf("Standard", "Class-I Heavy", "Bio-Mechanical Group").forEach { lvl ->
                    val isChosen = chosenLevel == lvl
                    Button(
                      onClick = { chosenLevel = lvl },
                      colors = ButtonDefaults.buttonColors(
                        containerColor = if (isChosen) Brass else DarkSurfaceVariant,
                        contentColor = if (isChosen) MidnightBlue else Brass
                      ),
                      modifier = Modifier.weight(1f),
                      shape = RoundedCornerShape(1.dp),
                      border = BorderStroke(1.dp, Brass),
                      contentPadding = PaddingValues(0.dp)
                    ) {
                      Text(text = lvl, style = MaterialTheme.typography.labelSmall)
                    }
                  }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                  onClick = {
                    onRegisterPermit(newPermitName, chosenType, chosenLevel)
                    newPermitName = ""
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = Brass, contentColor = MidnightBlue),
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register_permit_button"),
                  shape = RoundedCornerShape(2.dp)
                ) {
                  Text("TRANSMIT LICENSE REQUEST", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                }
              }
            }
          }

          // Listed permits list
          item {
            Text(
              text = "PROCLAIMED COMMERCIAL LICENSES",
              style = MaterialTheme.typography.labelSmall,
              color = SteelGrey,
              modifier = Modifier.padding(top = 8.dp)
            )
          }

          items(state.industrialPermits) { permit ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = DarkSurface),
              border = BorderStroke(1.dp, SteelGrey.copy(alpha = 0.4f))
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(text = permit.businessName.uppercase(), style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = Cream)
                  Text(text = "DOMAIN: ${permit.firmType} | SAFETY GROUP: ${permit.permitLevel}", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                }

                Box(
                  modifier = Modifier
                    .border(BorderStroke(1.dp, Brass))
                    .padding(vertical = 2.dp, horizontal = 6.dp)
                ) {
                  Text(text = permit.status.uppercase(), style = MaterialTheme.typography.labelSmall, color = LightGreen)
                }
              }
            }
          }
        }

        "PROJECTS" -> {
          // PUBLIC WORKS PORTAL
          item {
            Text(
              text = "PUBLIC INFRASTRUCTURE WORKS DIRECTORY",
              style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "All capital assets currently funded or under physical construction in the sovereign region.",
              style = MaterialTheme.typography.bodySmall,
              color = SteelGrey
            )
          }

          items(state.publicWorks) { proj ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = DarkSurface),
              border = BorderStroke(1.dp, BrassMuted),
              onClick = { onSelectProj(if (selectedProjId == proj.id) null else proj.id) }
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(text = proj.name.uppercase(), style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = Cream)
                    Text(text = "CONTRACTOR: ${proj.contractor.uppercase()}", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  }
                  Icon(
                    imageVector = if (selectedProjId == proj.id) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand info",
                    tint = Brass
                  )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text("PROGRESS CERTIFICATE", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  Text("${proj.progressPercent}%", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace), color = LightGreen)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                  progress = proj.progressPercent / 100f,
                  modifier = Modifier.fillMaxWidth().height(6.dp),
                  color = LightGreen,
                  trackColor = DarkBackground
                )

                // Additional details if expanded
                AnimatedVisibility(visible = selectedProjId == proj.id) {
                  Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = SteelGrey.copy(alpha = 0.3f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = proj.description, style = MaterialTheme.typography.bodyMedium, color = Cream)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                      Column {
                        Text("BUDGETARY EXPENDITURE", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                        Text("£${proj.cost} Million par", style = MaterialTheme.typography.labelMedium, color = Brass)
                      }
                      Column(horizontalAlignment = Alignment.End) {
                        Text("PROJECTION COMPLETED", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                        Text(proj.completionForecast.uppercase(), style = MaterialTheme.typography.labelMedium, color = Cream)
                      }
                    }
                  }
                }
              }
            }
          }
        }

        "ARCHIVES" -> {
          // GENERAL LAND & CHARTER ARCHIVES (KNOWLEDGE BANK)
          item {
            Text(
              text = "METROPOLITAN SOVEREIGN LIBRARY & CHARTER LEOPARDS",
              style = MaterialTheme.typography.labelLarge.copy(color = Brass, letterSpacing = 1.5.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Query active property rolls, early blueprints, charters, and gazettes.",
              style = MaterialTheme.typography.bodySmall,
              color = SteelGrey
            )
          }

          val filteredArchives = state.archives.filter {
            it.title.contains(state.currentSearchQuery, ignoreCase = true) ||
              it.category.contains(state.currentSearchQuery, ignoreCase = true) ||
              it.synopsis.contains(state.currentSearchQuery, ignoreCase = true)
          }

          if (filteredArchives.isEmpty()) {
            item {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .border(BorderStroke(1.dp, RustRed))
                  .padding(18.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "NO SECURE RECORDS SPECIFIED BY INBOUND QUERY",
                  style = MaterialTheme.typography.labelMedium.copy(color = RustRed)
                )
              }
            }
          }

          items(filteredArchives) { doc ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = DarkSurface),
              border = BorderStroke(1.dp, BrassMuted),
              onClick = { onSelectDoc(if (selectedDocId == doc.id) null else doc.id) }
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(text = doc.title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif), color = Cream)
                    Text(text = "CLASSIFIED CODE: ${doc.documentCode} | YEAR ${doc.year}", style = MaterialTheme.typography.labelSmall, color = SteelGrey)
                  }

                  Box(
                    modifier = Modifier
                      .background(DarkBackground)
                      .border(BorderStroke(1.dp, Brass))
                      .padding(vertical = 2.dp, horizontal = 6.dp)
                  ) {
                    Text(text = doc.category.uppercase(), style = MaterialTheme.typography.labelSmall, color = Brass)
                  }
                }

                AnimatedVisibility(visible = selectedDocId == doc.id) {
                  Column(modifier = Modifier.padding(top = 10.dp)) {
                    HorizontalDivider(color = BrassMuted.copy(alpha = 0.3f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                      text = doc.synopsis,
                      style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 21.sp),
                      color = Cream
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
